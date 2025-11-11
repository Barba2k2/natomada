package com.barbatech.natomada.stations.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain Entity: Station (Charging Station)
 *
 * Represents an electric vehicle charging station with data from
 * OpenChargeMap and enriched with Google Places information
 */
@Entity
@Table(name = "stations", indexes = {
    @Index(name = "idx_station_ocm_id", columnList = "ocm_id"),
    @Index(name = "idx_station_location", columnList = "latitude, longitude")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // External IDs
    @Column(name = "ocm_id", nullable = false, unique = true, length = 255)
    private String ocmId;

    @Column(name = "ocm_uuid", length = 100)
    private String ocmUuid;

    @Column(name = "google_place_id", length = 255)
    private String googlePlaceId;

    // Basic Information
    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(length = 50)
    private String phone;

    // Operational Status
    @Builder.Default
    @Column(name = "is_operational", nullable = false)
    private Boolean isOperational = true;

    @Builder.Default
    @Column(name = "total_connectors", nullable = false)
    private Integer totalConnectors = 0;

    // Connectors (JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String connectors;

    // Operator Information
    @Column(name = "operator_name", length = 255)
    private String operatorName;

    @Column(name = "operator_website", length = 500)
    private String operatorWebsite;

    @Column(name = "operator_phone", length = 50)
    private String operatorPhone;

    @Column(name = "operator_email", length = 255)
    private String operatorEmail;

    // Usage Type
    @Column(name = "usage_type", length = 100)
    private String usageType;

    @Builder.Default
    @Column(name = "requires_membership", nullable = false)
    private Boolean requiresMembership = false;

    @Builder.Default
    @Column(name = "pay_at_location", nullable = false)
    private Boolean payAtLocation = false;

    @Builder.Default
    @Column(name = "requires_access_key", nullable = false)
    private Boolean requiresAccessKey = false;

    // Cost
    @Column(name = "usage_cost", length = 255)
    private String usageCost;

    // OCM Ratings
    @Column(name = "ocm_rating", precision = 3, scale = 2)
    private BigDecimal ocmRating;

    @Builder.Default
    @Column(name = "ocm_review_count", nullable = false)
    private Integer ocmReviewCount = 0;

    // Google Ratings
    @Column(name = "google_rating", precision = 3, scale = 2)
    private BigDecimal googleRating;

    @Builder.Default
    @Column(name = "google_review_count", nullable = false)
    private Integer googleReviewCount = 0;

    // Combined Rating
    @Column(name = "combined_rating", precision = 3, scale = 2)
    private BigDecimal combinedRating;

    @Builder.Default
    @Column(name = "total_reviews", nullable = false)
    private Integer totalReviews = 0;

    // Opening Hours (JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "opening_hours", columnDefinition = "jsonb")
    private String openingHours;

    @Builder.Default
    @Column(name = "is_open_24h", nullable = false)
    private Boolean isOpen24h = false;

    // Photo References (JSON)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "photo_references", columnDefinition = "jsonb")
    private String photoReferences;

    // Amenities (JSON array of strings)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "amenities", columnDefinition = "jsonb")
    private String amenities;

    // Metadata
    @Column(name = "last_verified_at")
    private LocalDateTime lastVerifiedAt;

    @Builder.Default
    @Column(name = "is_recently_verified", nullable = false)
    private Boolean isRecentlyVerified = false;

    @Column(name = "last_sync_at")
    private LocalDateTime lastSyncAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
