package com.barbatech.natomada.stations.application.services;

import com.barbatech.natomada.infrastructure.i18n.MessageSourceService;
import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.external.ExternalStationMapper;
import com.barbatech.natomada.stations.infrastructure.external.google.GooglePlacesService;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
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

        // Step 2: Fetch from Google Places (enrichment source)
        try {
            GooglePlacesResponse googleResponse = googlePlacesService.searchNearby(
                latitude,
                longitude,
                radius
            );

            if (googleResponse.getResults() != null) {
                log.info("Fetched {} places from Google Places", googleResponse.getResults().size());

                // Try to match Google Places with OpenChargeMap stations by proximity
                for (GooglePlacesResponse.Place place : googleResponse.getResults()) {
                    matchAndEnrichStation(allStations, place);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching from Google Places: {}", e.getMessage(), e);
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

        // Try to enrich with Google Places data if we have coordinates
        if (station.getLatitude() != null && station.getLongitude() != null) {
            try {
                GooglePlacesResponse googleResponse = googlePlacesService.searchNearby(
                    station.getLatitude().doubleValue(),
                    station.getLongitude().doubleValue(),
                    100 // 100 meters radius for detail lookup
                );

                if (googleResponse.getResults() != null && !googleResponse.getResults().isEmpty()) {
                    // Find closest match
                    for (GooglePlacesResponse.Place place : googleResponse.getResults()) {
                        if (place.getGeometry() != null && place.getGeometry().getLocation() != null) {
                            double distance = calculateDistance(
                                station.getLatitude().doubleValue(),
                                station.getLongitude().doubleValue(),
                                place.getGeometry().getLocation().getLat().doubleValue(),
                                place.getGeometry().getLocation().getLng().doubleValue()
                            );

                            // If within 50 meters, consider it a match
                            if (distance < 0.05) {
                                externalStationMapper.enrichWithGooglePlaces(station, place);
                                log.info("Enriched station with Google Places data");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                log.warn("Could not enrich station with Google Places data: {}", e.getMessage());
            }
        }

        log.info("Found station from APIs: {}", station.getName());

        return mapToResponse(station);
    }

    /**
     * Build complete Google Places Photo URL from photo reference
     */
    private String buildPhotoUrl(String photoReference) {
        return String.format(
            "https://maps.googleapis.com/maps/api/place/photo?maxwidth=800&photo_reference=%s&key=%s",
            photoReference,
            googlePlacesApiKey
        );
    }
}
