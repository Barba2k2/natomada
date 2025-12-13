package com.barbatech.natomada.stations.application.services;

import com.barbatech.natomada.infrastructure.i18n.MessageSourceService;
import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.external.ExternalStationMapper;
import com.barbatech.natomada.stations.infrastructure.external.google.GooglePlacesService;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.PlacesV1Response;
import com.barbatech.natomada.stations.infrastructure.external.opencm.OpenChargeMapService;
import com.barbatech.natomada.stations.infrastructure.external.opencm.dtos.OpenChargeMapResponse;
import com.barbatech.natomada.stations.infrastructure.repositories.FavoriteRepository;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for station operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StationsService {

    private final StationRepository stationRepository;
    private final FavoriteRepository favoriteRepository;
    private final OpenChargeMapService openChargeMapService;
    private final GooglePlacesService googlePlacesService;
    private final ExternalStationMapper externalStationMapper;
    private final ObjectMapper objectMapper;
    private final MessageSourceService messageService;

    @Value("${google.places.api.key}")
    private String googlePlacesApiKey;

    /**
     * Get nearby stations from external APIs (OpenChargeMap + Google Places)
     *
     * Strategy:
     * 1. Fetch stations from OpenChargeMap (primary source)
     * 2. Fetch stations from Google Places (enrichment)
     * 3. Merge and enrich data
     * 4. Return combined results
     *
     * Cached for 5 minutes to improve performance (external APIs are slow)
     */
    @Cacheable(
        value = "nearby-stations",
        key = "#latitude.toString().substring(0, 6) + '_' + #longitude.toString().substring(0, 6) + '_' + #radius + '_' + #limit + '_' + (#userId != null ? #userId : 'anon')"
    )
    @Transactional
    public NearbyStationsResult getNearbyStations(
        Double latitude,
        Double longitude,
        Integer radius,
        Integer limit,
        Long userId
    ) {
        log.info("Fetching nearby stations from external APIs: lat={}, lon={}, radius={}m, limit={}",
                 latitude, longitude, radius, limit);

        List<Station> allStations = new ArrayList<>();
        boolean ocmAvailable = false;

        // Step 1: Fetch from OpenChargeMap (primary source)
        try {
            List<OpenChargeMapResponse> ocmStations = openChargeMapService.searchNearby(
                latitude,
                longitude,
                radius / 1000, // Convert meters to kilometers
                limit != null ? limit : 50
            );

            for (OpenChargeMapResponse ocmStation : ocmStations) {
                Station station = externalStationMapper.fromOpenChargeMap(ocmStation);
                allStations.add(station);
            }

            // OCM is only truly available if it returned actual data
            // Empty result likely means timeout/error without exception
            ocmAvailable = !ocmStations.isEmpty();
            log.info("Fetched {} stations from OpenChargeMap, OCM available: {}", ocmStations.size(), ocmAvailable);
        } catch (Exception e) {
            ocmAvailable = false; // OCM failed
            log.error("Error fetching from OpenChargeMap: {}", e.getMessage(), e);
        }

        // Step 2: Fetch from Google Places API v1 (enrichment source with EV connector data)
        try {
            PlacesV1Response googleResponse = googlePlacesService.searchNearbyV1(
                latitude,
                longitude,
                radius
            );

            if (googleResponse.getPlaces() != null) {
                log.info("Fetched {} places from Google Places v1", googleResponse.getPlaces().size());

                // If OpenChargeMap returned stations, try to enrich them with Google data
                if (!allStations.isEmpty()) {
                    // Try to match Google Places with OpenChargeMap stations by proximity
                    for (PlacesV1Response.Place place : googleResponse.getPlaces()) {
                        matchAndEnrichStationV1(allStations, place);
                    }
                } else {
                    // OpenChargeMap is down or returned no results - use Google Places as primary source
                    log.info("No OpenChargeMap stations available, using Google Places as primary source");
                    for (PlacesV1Response.Place place : googleResponse.getPlaces()) {
                        Station station = externalStationMapper.fromGooglePlacesV1(place);
                        allStations.add(station);
                    }
                    log.info("Created {} stations from Google Places v1", allStations.size());
                }
            }
        } catch (Exception e) {
            log.error("Error fetching from Google Places v1: {}", e.getMessage(), e);
        }

        // Step 3: Filter stations by actual distance (OpenChargeMap may return stations beyond radius)
        List<Station> stationsWithinRadius = new ArrayList<>();
        for (Station station : allStations) {
            if (station.getLatitude() != null && station.getLongitude() != null) {
                double distanceKm = calculateHaversineDistance(
                    latitude,
                    longitude,
                    station.getLatitude().doubleValue(),
                    station.getLongitude().doubleValue()
                );

                // Only include stations within the requested radius
                double radiusKm = radius / 1000.0;
                if (distanceKm <= radiusKm) {
                    stationsWithinRadius.add(station);
                } else {
                    log.debug("Excluding station {} - distance {}km exceeds radius {}km",
                             station.getName(), distanceKm, radiusKm);
                }
            } else {
                stationsWithinRadius.add(station); // Include stations without coordinates
            }
        }

        log.info("Filtered {} stations to {} within {}km radius",
                 allStations.size(), stationsWithinRadius.size(), radius / 1000.0);

        // Step 4: Save all stations to database for future fast access
        List<Station> savedStations = new ArrayList<>();
        for (Station station : stationsWithinRadius) {
            try {
                // Check if station already exists
                Optional<Station> existing = stationRepository.findByOcmId(station.getOcmId());
                if (existing.isPresent()) {
                    savedStations.add(existing.get());
                } else {
                    // Save new station
                    Station saved = stationRepository.save(station);
                    log.debug("Cached station in database: {} (ID: {})", saved.getOcmId(), saved.getId());
                    savedStations.add(saved);
                }
            } catch (Exception e) {
                log.warn("Failed to save station {} to database: {}", station.getOcmId(), e.getMessage());
                savedStations.add(station); // Add unsaved station anyway
            }
        }

        // Step 5: Limit results
        if (limit != null && savedStations.size() > limit) {
            savedStations = savedStations.subList(0, limit);
        }

        log.info("Returning {} total stations ({} cached in database), OCM available: {}",
                 savedStations.size(),
                 savedStations.stream().filter(s -> s.getId() != null).count(),
                 ocmAvailable);

        List<StationResponseDto> stationDtos = savedStations.stream()
            .map(station -> mapToResponse(station, userId))
            .collect(Collectors.toList());

        return NearbyStationsResult.builder()
            .stations(stationDtos)
            .ocmAvailable(ocmAvailable)
            .build();
    }

    /**
     * Try to match a Google Place with existing stations and enrich them
     * If no match found, this could be a new station (future enhancement)
     */
    private void matchAndEnrichStation(List<Station> stations, GooglePlacesResponse.Place place) {
        if (place.getGeometry() == null || place.getGeometry().getLocation() == null) {
            return;
        }

        BigDecimal placeLat = place.getGeometry().getLocation().getLat();
        BigDecimal placeLon = place.getGeometry().getLocation().getLng();

        // Find closest station within 100m
        double minDistance = 0.1; // ~100 meters in degrees
        Station closest = null;

        for (Station station : stations) {
            if (station.getLatitude() == null || station.getLongitude() == null) {
                continue;
            }

            double distance = calculateDistance(
                station.getLatitude().doubleValue(),
                station.getLongitude().doubleValue(),
                placeLat.doubleValue(),
                placeLon.doubleValue()
            );

            if (distance < minDistance) {
                minDistance = distance;
                closest = station;
            }
        }

        if (closest != null) {
            // Enrich existing station with Google data
            externalStationMapper.enrichWithGooglePlaces(closest, place);
            log.debug("Enriched station {} with Google Places data", closest.getName());
        }
        // Note: If no match found, we could create a new station from Google data
        // This is a future enhancement
    }

    /**
     * Try to match a Google Place v1 with existing stations and enrich them with EV connector data
     * If no match found, this could be a new station (future enhancement)
     */
    private void matchAndEnrichStationV1(List<Station> stations, PlacesV1Response.Place place) {
        if (place.getLocation() == null) {
            return;
        }

        BigDecimal placeLat = place.getLocation().getLatitude();
        BigDecimal placeLon = place.getLocation().getLongitude();

        // Find closest station within 150m (same as detail view)
        double minDistance = 0.15; // ~150 meters in degrees
        Station closest = null;

        for (Station station : stations) {
            if (station.getLatitude() == null || station.getLongitude() == null) {
                continue;
            }

            double distance = calculateDistance(
                station.getLatitude().doubleValue(),
                station.getLongitude().doubleValue(),
                placeLat.doubleValue(),
                placeLon.doubleValue()
            );

            if (distance < minDistance) {
                minDistance = distance;
                closest = station;
            }
        }

        if (closest != null) {
            // Enrich existing station with Google Places v1 data (includes EV connectors)
            externalStationMapper.enrichWithGooglePlacesV1(closest, place);
            String stationName = place.getDisplayName() != null ? place.getDisplayName().getText() : "Unknown";
            log.debug("Enriched station {} with Google Places v1 data (including EV connectors)", stationName);
        }
        // Note: If no match found, we could create a new station from Google data
        // This is a future enhancement
    }

    /**
     * Calculate simple distance between two coordinates
     * Returns distance in degrees (approximate)
     */
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDiff = lat1 - lat2;
        double lonDiff = lon1 - lon2;
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }

    /**
     * Calculate accurate distance between two coordinates using Haversine formula
     * Returns distance in kilometers
     */
    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final double earthRadiusKm = 6371.0;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return earthRadiusKm * c;
    }

    /**
     * Map Station entity to response DTO
     */
    private StationResponseDto mapToResponse(Station station, Long userId) {
        // Parse photo references from JSON and convert to URLs
        List<String> photoUrls = new ArrayList<>();
        if (station.getPhotoReferences() != null) {
            try {
                List<String> photoRefs = objectMapper.readValue(
                    station.getPhotoReferences(),
                    new TypeReference<List<String>>() {}
                );

                // Convert photo references to complete URLs
                photoUrls = photoRefs.stream()
                    .map(this::buildPhotoUrl)
                    .collect(Collectors.toList());
            } catch (Exception e) {
                log.warn("Error parsing photo references for station {}: {}", station.getName(), e.getMessage());
            }
        }

        // Amenities are now type-safe - no JSON parsing needed
        // Following Axel Engineering Doctrine: explicit types over strings

        // Check if station is in user's favorites
        boolean isFavorite = false;
        if (userId != null && station.getId() != null) {
            isFavorite = favoriteRepository.existsByUserIdAndStationId(userId, station.getId());
        }

        return StationResponseDto.builder()
            .id(station.getId())
            .ocmId(station.getOcmId())
            .ocmUuid(station.getOcmUuid())
            .googlePlaceId(station.getGooglePlaceId())
            .name(station.getName())
            .address(station.getAddress())
            .city(station.getCity())
            .state(station.getState())
            .postalCode(station.getPostalCode())
            .country(station.getCountry())
            .latitude(station.getLatitude())
            .longitude(station.getLongitude())
            .phone(station.getPhone())
            .isOperational(station.getIsOperational())
            .totalConnectors(station.getTotalConnectors())
            .connectors(station.getConnectors())  // Type-safe connectors
            .operator(StationResponseDto.OperatorDto.builder()
                .name(station.getOperatorName())
                .website(station.getOperatorWebsite())
                .phone(station.getOperatorPhone())
                .email(station.getOperatorEmail())
                .build())
            .usageType(StationResponseDto.UsageTypeDto.builder()
                .title(station.getUsageType())
                .requiresMembership(station.getRequiresMembership())
                .payAtLocation(station.getPayAtLocation())
                .requiresAccessKey(station.getRequiresAccessKey())
                .build())
            .usageCost(station.getUsageCost())
            .rating(StationResponseDto.RatingDto.builder()
                .ocm(station.getOcmRating())
                .ocmCount(station.getOcmReviewCount())
                .google(station.getGoogleRating())
                .googleCount(station.getGoogleReviewCount())
                .combined(station.getCombinedRating())
                .build())
            .totalReviews(station.getTotalReviews())
            .openingHours(station.getOpeningHours())  // Type-safe opening hours
            .isOpen24h(station.getIsOpen24h())
            .photoUrls(photoUrls)
            .amenities(station.getAmenities())  // Type-safe amenities
            .lastVerifiedAt(station.getLastVerifiedAt())
            .isRecentlyVerified(station.getIsRecentlyVerified())
            .lastSyncAt(station.getLastSyncAt())
            .isFavorite(isFavorite)
            .build();
    }

    /**
     * Get station by ID from external APIs (OpenChargeMap + Google Places)
     *
     * @param stationId The station ID (format: "ocm_123456")
     * @param userId The user ID (optional, for checking favorite status)
     * @return Station details
     */
    public StationResponseDto getStationById(String stationId, Long userId) {
        log.info("Fetching station by ID: {}", stationId);

        // Use getOrFetchStationByOcmId to ensure station is saved in database
        // This is necessary for favorites functionality (needs database ID)
        // This method already handles enrichment with Google Places data
        Station station = getOrFetchStationByOcmId(stationId);

        log.info("Found station: {} (rating: {})", station.getName(), station.getCombinedRating());

        return mapToResponse(station, userId);
    }

    /**
     * Try to find nearby businesses (like dealerships) with photos when charging station has none
     */
    private void tryEnrichWithNearbyBusinessPhotos(Station station) {
        try {
            log.info("Searching for nearby businesses with photos for station: {}", station.getName());

            // Search for any nearby place (not just charging stations) within 50 meters
            GooglePlacesResponse nearbyResponse = googlePlacesService.searchNearbyBusiness(
                station.getLatitude().doubleValue(),
                station.getLongitude().doubleValue(),
                50 // 50 meters - very close proximity
            );

            if (nearbyResponse.getResults() != null && !nearbyResponse.getResults().isEmpty()) {
                // Find the closest place with photos
                for (GooglePlacesResponse.Place place : nearbyResponse.getResults()) {
                    if (place.getGeometry() != null && place.getGeometry().getLocation() != null) {
                        double distance = calculateDistance(
                            station.getLatitude().doubleValue(),
                            station.getLongitude().doubleValue(),
                            place.getGeometry().getLocation().getLat().doubleValue(),
                            place.getGeometry().getLocation().getLng().doubleValue()
                        );

                        // Within 50 meters (0.05 degrees ≈ 5.5km, so 50m ≈ 0.0005)
                        if (distance < 0.001) {
                            // Fetch full details to get photos
                            GooglePlacesResponse.Place placeDetails = googlePlacesService.getPlaceDetailsAsPlace(place.getPlaceId());
                            if (placeDetails != null && placeDetails.getPhotos() != null && !placeDetails.getPhotos().isEmpty()) {
                                try {
                                    List<String> photoRefs = new ArrayList<>();
                                    int maxPhotos = Math.min(placeDetails.getPhotos().size(), 5);
                                    for (int i = 0; i < maxPhotos; i++) {
                                        String photoRef = placeDetails.getPhotos().get(i).getPhotoReference();
                                        if (photoRef != null) {
                                            photoRefs.add(photoRef);
                                        }
                                    }
                                    if (!photoRefs.isEmpty()) {
                                        station.setPhotoReferences(objectMapper.writeValueAsString(photoRefs));
                                        log.info("Added {} photos from nearby business '{}' (distance: {}m)",
                                            photoRefs.size(), place.getName(), distance * 111000);
                                        return; // Success - stop searching
                                    }
                                } catch (Exception e) {
                                    log.warn("Error processing photos from nearby business: {}", e.getMessage());
                                }
                            }
                        }
                    }
                }
            }
            log.info("No nearby businesses with photos found for station: {}", station.getName());
        } catch (Exception e) {
            log.warn("Error searching for nearby business photos: {}", e.getMessage());
        }
    }

    /**
     * Add Street View photo as fallback when no other photos available
     */
    private void addStreetViewPhoto(Station station) {
        try {
            String streetViewUrl = String.format(
                "https://maps.googleapis.com/maps/api/streetview?size=800x600&location=%s,%s&key=%s",
                station.getLatitude(),
                station.getLongitude(),
                googlePlacesApiKey
            );

            // Store as a special marker that this is a Street View photo (not a photo reference)
            List<String> photoUrls = new ArrayList<>();
            photoUrls.add("streetview:" + streetViewUrl);
            station.setPhotoReferences(objectMapper.writeValueAsString(photoUrls));
            log.info("Added Street View photo for station: {}", station.getName());
        } catch (Exception e) {
            log.warn("Error adding Street View photo: {}", e.getMessage());
        }
    }

    /**
     * Get or fetch station by OCM ID
     * First tries to find in database, if not found fetches from external APIs and saves
     *
     * @param ocmId The OpenChargeMap ID (format: "ocm_123")
     * @return Station entity (from DB or freshly fetched and saved)
     */
    @Transactional
    public Station getOrFetchStationByOcmId(String ocmId) {
        log.info("Getting or fetching station by OCM ID: {}", ocmId);

        // Try to find in database first
        Optional<Station> existingStation = stationRepository.findByOcmId(ocmId);
        if (existingStation.isPresent()) {
            log.info("Station found in database: {}", ocmId);
            return existingStation.get();
        }

        // Not in database, fetch from external APIs
        log.info("Station not in database, fetching from external APIs: {}", ocmId);

        // Parse OCM ID to integer
        Integer ocmIdInt;
        try {
            ocmIdInt = Integer.parseInt(ocmId.replace("ocm_", ""));
        } catch (NumberFormatException e) {
            log.error("Invalid OCM ID format: {}", ocmId);
            throw new IllegalArgumentException(messageService.getMessage("station.id.invalid"));
        }

        // Fetch from OpenChargeMap API
        OpenChargeMapResponse ocmStation = openChargeMapService.getById(ocmIdInt);
        if (ocmStation == null) {
            log.error("Station not found in OpenChargeMap: {}", ocmId);
            throw new RuntimeException(messageService.getMessage("station.not.found"));
        }

        // Convert to Station entity
        Station station = externalStationMapper.fromOpenChargeMap(ocmStation);

        // Try to enrich with Google Places data (same logic as getStationById)
        if (station.getLatitude() != null && station.getLongitude() != null) {
            try {
                log.info("Enriching station with Google Places v1 data");
                PlacesV1Response placesV1Response = googlePlacesService.searchNearbyV1(
                    station.getLatitude().doubleValue(),
                    station.getLongitude().doubleValue(),
                    150 // 150 meters radius
                );

                if (placesV1Response != null && placesV1Response.getPlaces() != null && !placesV1Response.getPlaces().isEmpty()) {
                    // Find closest match
                    double minDistance = Double.MAX_VALUE;
                    PlacesV1Response.Place closestPlace = null;

                    for (PlacesV1Response.Place place : placesV1Response.getPlaces()) {
                        if (place.getLocation() != null) {
                            double distance = calculateDistance(
                                station.getLatitude().doubleValue(),
                                station.getLongitude().doubleValue(),
                                place.getLocation().getLatitude().doubleValue(),
                                place.getLocation().getLongitude().doubleValue()
                            );

                            if (distance < minDistance) {
                                minDistance = distance;
                                closestPlace = place;
                            }
                        }
                    }

                    // If within 150 meters, enrich
                    if (closestPlace != null && minDistance < 0.15) {
                        externalStationMapper.enrichWithGooglePlacesV1(station, closestPlace);
                        log.info("Enriched station with Google Places v1 data (distance: {}m)", minDistance * 111000);
                    }
                }
            } catch (Exception e) {
                log.warn("Could not enrich station with Google Places: {}", e.getMessage());
            }
        }

        // Save to database
        Station savedStation = stationRepository.save(station);
        log.info("Station saved to database: {} (ID: {})", savedStation.getOcmId(), savedStation.getId());

        return savedStation;
    }

    /**
     * Build complete Google Places Photo URL from photo reference
     */
    private String buildPhotoUrl(String photoReference) {
        // Check if this is a Street View URL (starts with "streetview:")
        if (photoReference.startsWith("streetview:")) {
            return photoReference.substring(11); // Remove "streetview:" prefix
        }

        // Regular Google Places photo
        return String.format(
            "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference=%s&key=%s",
            photoReference,
            googlePlacesApiKey
        );
    }

    /**
     * Result wrapper for nearby stations search
     * Includes whether OpenChargeMap is available for filtering
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NearbyStationsResult {
        private List<StationResponseDto> stations;
        private boolean ocmAvailable;
    }
}
