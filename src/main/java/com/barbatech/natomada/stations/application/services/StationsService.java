package com.barbatech.natomada.stations.application.services;

import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for station operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StationsService {

    private final StationRepository stationRepository;

    /**
     * Get nearby stations
     *
     * NOTE: This is a simplified implementation that searches within a bounding box.
     * For production, you should:
     * 1. Integrate with OpenChargeMap API
     * 2. Enrich with Google Places API
     * 3. Use PostGIS for accurate distance calculations
     */
    @Transactional(readOnly = true)
    public List<StationResponseDto> getNearbyStations(
        Double latitude,
        Double longitude,
        Integer radius,
        Integer limit
    ) {
        log.info("Fetching nearby stations: lat={}, lon={}, radius={}m, limit={}",
                 latitude, longitude, radius, limit);

        // Calculate approximate bounding box
        // 1 degree latitude ≈ 111km
        // 1 degree longitude ≈ 111km * cos(latitude)
        double radiusInDegrees = radius / 111000.0;
        BigDecimal minLat = BigDecimal.valueOf(latitude - radiusInDegrees);
        BigDecimal maxLat = BigDecimal.valueOf(latitude + radiusInDegrees);
        BigDecimal minLon = BigDecimal.valueOf(longitude - radiusInDegrees);
        BigDecimal maxLon = BigDecimal.valueOf(longitude + radiusInDegrees);

        // Find stations in bounding box
        List<Station> stations = stationRepository.findNearbyStations(
            minLat, maxLat, minLon, maxLon
        );

        // Limit results
        if (limit != null && stations.size() > limit) {
            stations = stations.subList(0, limit);
        }

        log.info("Found {} stations", stations.size());

        return stations.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
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
