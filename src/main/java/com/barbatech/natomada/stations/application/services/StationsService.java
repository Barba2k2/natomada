package com.barbatech.natomada.stations.application.services;

import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.external.ExternalStationMapper;
import com.barbatech.natomada.stations.infrastructure.external.google.GooglePlacesService;
import com.barbatech.natomada.stations.infrastructure.external.google.dtos.GooglePlacesResponse;
import com.barbatech.natomada.stations.infrastructure.external.opencm.OpenChargeMapService;
import com.barbatech.natomada.stations.infrastructure.external.opencm.dtos.OpenChargeMapResponse;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return StationResponseDto.builder()
            .id(station.getId())
            .ocmId(station.getOcmId())
            .ocmUuid(station.getOcmUuid())
            .googlePlaceId(station.getGooglePlaceId())
            .externalId(station.getExternalId())
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
            .lastVerifiedAt(station.getLastVerifiedAt())
            .isRecentlyVerified(station.getIsRecentlyVerified())
            .lastSyncAt(station.getLastSyncAt())
            .createdAt(station.getCreatedAt())
            .updatedAt(station.getUpdatedAt())
            .build();
    }
}
