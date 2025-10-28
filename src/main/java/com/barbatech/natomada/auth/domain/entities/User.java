package com.barbatech.natomada.auth.domain.entities;

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
}
