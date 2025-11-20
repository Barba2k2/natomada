package com.barbatech.natomada.auth.domain.entities;

import com.barbatech.natomada.auth.application.exceptions.PasswordMismatchException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain Entity: User
 *
 * Represents the user entity in the application domain.
 * This is a JPA entity mapped to the 'users' table.
 */
@Entity
@Table(name = "users", indexes = {
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_phone", columnList = "phone")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Builder.Default
    @Column(name = "total_charges", nullable = false)
    private Integer totalCharges = 0;

    @Builder.Default
    @Column(name = "total_kwh_charged", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalKwhCharged = BigDecimal.ZERO;

    @Builder.Default
    @Column(name = "total_stations_visited", nullable = false)
    private Integer totalStationsVisited = 0;

    @Column(name = "email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    /**
     * Checks if the email has been verified
     */
    public boolean isEmailVerified() {
        return emailVerifiedAt != null;
    }

    /**
     * Mark email as verified
     */
    public void verifyEmail() {
        this.emailVerifiedAt = LocalDateTime.now();
    }

    /**
     * DOMAIN VALIDATION: Validate password match
     *
     * Business rule: Password and confirmation must match.
     * This is domain logic, not application logic.
     *
     * Following Axel Engineering Doctrine:
     * - Predictability: Clear validation, no hidden behavior
     * - Domain Layer: Business rules belong in domain
     * - Explicitness: Typed exceptions, no string matching
     *
     * @param password the password
     * @param confirmation the password confirmation
     * @throws PasswordMismatchException if passwords don't match
     */
    public static void validatePasswordMatch(String password, String confirmation) {
        if (password == null || confirmation == null) {
            throw new PasswordMismatchException();
        }
        if (!password.equals(confirmation)) {
            throw new PasswordMismatchException();
        }
    }

    /**
     * DOMAIN LOGIC: Check if user can use charging stations
     *
     * Business rule: User must have verified email to use charging stations
     *
     * @return true if user can use charging stations
     */
    public boolean canUseChargingStations() {
        return isEmailVerified();
    }

    /**
     * DOMAIN LOGIC: Record charging session
     *
     * Business rule: Updates user's charging statistics
     * - Increments total charges
     * - Adds kWh to total charged
     *
     * @param kwhCharged amount of kWh charged in this session
     * @throws IllegalArgumentException if kwhCharged is null or non-positive
     */
    public void recordChargingSession(BigDecimal kwhCharged) {
        if (kwhCharged == null || kwhCharged.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("kWh must be positive");
        }

        this.totalCharges++;
        this.totalKwhCharged = this.totalKwhCharged.add(kwhCharged);
    }

    /**
     * DOMAIN LOGIC: Record station visit
     *
     * Business rule: Increments the count of stations visited by user
     */
    public void recordStationVisit() {
        this.totalStationsVisited++;
    }
}
