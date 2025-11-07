package com.barbatech.natomada.auth.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Domain Entity: OtpToken
 *
 * Represents an OTP (One-Time Password) token for phone verification.
 * Tokens expire after 5 minutes and can only be used once.
 */
@Entity
@Table(name = "otp_tokens", indexes = {
    @Index(name = "idx_otp_phone", columnList = "phone_number"),
    @Index(name = "idx_otp_expires_at", columnList = "expires_at")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OtpToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(nullable = false, length = 6)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Checks if the OTP has expired
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    /**
     * Checks if the OTP has been verified
     */
    public boolean isVerified() {
        return verifiedAt != null;
    }

    /**
     * Checks if the OTP is valid (not expired and not verified)
     */
    public boolean isValid() {
        return !isExpired() && !isVerified();
    }

    /**
     * Mark the OTP as verified
     */
    public void markAsVerified() {
        this.verifiedAt = LocalDateTime.now();
    }
}
