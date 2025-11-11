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
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
     */
    @Transactional(readOnly = true)
    public List<StationResponseDto> getNearbyStations(
        Double latitude,
        Double longitude,
        Integer radius,
        Integer limit
    ) {
        log.info("Fetching nearby stations from external APIs: lat={}, lon={}, radius={}m, limit={}",
                 latitude, longitude, radius, limit);

        List<Station> allStations = new ArrayList<>();

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

            log.info("Fetched {} stations from OpenChargeMap", ocmStations.size());
        } catch (Exception e) {
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

                // Try to match Google Places with OpenChargeMap stations by proximity
                for (PlacesV1Response.Place place : googleResponse.getPlaces()) {
                    matchAndEnrichStationV1(allStations, place);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching from Google Places v1: {}", e.getMessage(), e);
        }

        // Step 3: Limit results
        if (limit != null && allStations.size() > limit) {
            allStations = allStations.subList(0, limit);
        }

        log.info("Returning {} total stations", allStations.size());

        return allStations.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
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
     * Map Station entity to response DTO
     */
    private StationResponseDto mapToResponse(Station station) {
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

        // Parse amenities from JSON
        List<String> amenities = new ArrayList<>();
        if (station.getAmenities() != null) {
            try {
                amenities = objectMapper.readValue(
                    station.getAmenities(),
                    new TypeReference<List<String>>() {}
                );
            } catch (Exception e) {
                log.warn("Error parsing amenities for station {}: {}", station.getName(), e.getMessage());
            }
        }

        return StationResponseDto.builder()
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
            .connectors(station.getConnectors())
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
            .openingHours(station.getOpeningHours())
            .isOpen24h(station.getIsOpen24h())
            .photoUrls(photoUrls)
            .amenities(amenities)
            .lastVerifiedAt(station.getLastVerifiedAt())
            .isRecentlyVerified(station.getIsRecentlyVerified())
            .lastSyncAt(station.getLastSyncAt())
            .build();
    }

    /**
     * Get station by ID from external APIs (OpenChargeMap + Google Places)
     *
     * @param stationId The station ID (format: "ocm_123456")
     * @return Station details
     */
    public StationResponseDto getStationById(String stationId) {
        log.info("Fetching station by ID from external APIs: {}", stationId);

        // Parse OCM ID from external ID format (e.g., "ocm_188927" -> 188927)
        Integer ocmId;
        try {
            ocmId = Integer.parseInt(stationId.replace("ocm_", ""));
        } catch (NumberFormatException e) {
            log.error("Invalid station ID format: {}", stationId);
            throw new RuntimeException(messageService.getMessage("station.id.invalid"));
        }

        // Fetch from OpenChargeMap API
        OpenChargeMapResponse ocmStation = openChargeMapService.getById(ocmId);
        if (ocmStation == null) {
            log.error("Station not found in OpenChargeMap: {}", stationId);
            throw new RuntimeException(messageService.getMessage("station.not.found"));
        }

        // Convert to Station entity
        Station station = externalStationMapper.fromOpenChargeMap(ocmStation);

        // Try to enrich with Google Places data
        boolean enriched = false;

        // Always try Places API v1 for EV connector data (has availability info)
        if (station.getLatitude() != null && station.getLongitude() != null) {
            try {
                log.info("Trying Places API v1 nearby search for station details");
                PlacesV1Response placesV1Response = googlePlacesService.searchNearbyV1(
                    station.getLatitude().doubleValue(),
                    station.getLongitude().doubleValue(),
                    150 // 150 meters radius for detail lookup
                );

                if (placesV1Response != null && placesV1Response.getPlaces() != null && !placesV1Response.getPlaces().isEmpty()) {
                    // Find closest match
                    double minDistance = Double.MAX_VALUE;
                    PlacesV1Response.Place closestPlace = null;

                    for (PlacesV1Response.Place place : placesV1Response.getPlaces()) {
                        if (place.getLocation() != null && place.getLocation().getLatitude() != null && place.getLocation().getLongitude() != null) {
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

                    // If within 150 meters, consider it a match (same threshold as list view)
                    if (closestPlace != null && minDistance < 0.15) {
                        externalStationMapper.enrichWithGooglePlacesV1(station, closestPlace);
                        enriched = true;
                        log.info("Enriched station with Google Places v1 API (distance: {}m)", minDistance * 111000);

                        // If no photos available, try to find nearby business with photos
                        if (station.getPhotoReferences() == null || station.getPhotoReferences().equals("[]")) {
                            tryEnrichWithNearbyBusinessPhotos(station);
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("Could not enrich station with Google Places v1 nearby search: {}", e.getMessage());
            }
        }

        // Strategy 3: If still no photos and we have coordinates, add Street View as fallback
        if (enriched && station.getLatitude() != null && station.getLongitude() != null) {
            if (station.getPhotoReferences() == null || station.getPhotoReferences().equals("[]")) {
                addStreetViewPhoto(station);
            }
        }

        if (!enriched) {
            log.warn("Station {} could not be enriched with Google Places data", station.getName());
        }

        log.info("Found station from APIs: {} (rating: {})", station.getName(), station.getCombinedRating());

        return mapToResponse(station);
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
}
