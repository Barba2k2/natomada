package com.barbatech.natomada.stations.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for station response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StationResponseDto {

    private String ocmId;
    private String ocmUuid;
    private String googlePlaceId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phone;
    private Boolean isOperational;
    private Integer totalConnectors;
    private String connectors; // JSON string
    private OperatorDto operator;
    private UsageTypeDto usageType;
    private String usageCost;
    private RatingDto rating;
    private Integer totalReviews;
    private String openingHours; // JSON string
    private Boolean isOpen24h;
    private List<String> photoUrls; // List of complete photo URLs (Google Places)
    private List<String> amenities; // List of amenities from Google Places
    private LocalDateTime lastVerifiedAt;
    private Boolean isRecentlyVerified;
    private LocalDateTime lastSyncAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OperatorDto {
        private String name;
        private String website;
        private String phone;
        private String email;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UsageTypeDto {
        private String title;
        private Boolean requiresMembership;
        private Boolean payAtLocation;
        private Boolean requiresAccessKey;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingDto {
        private BigDecimal ocm;
        private Integer ocmCount;
        private BigDecimal google;
        private Integer googleCount;
        private BigDecimal combined;
    }
}
