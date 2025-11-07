package com.barbatech.natomada.auth.infrastructure.repositories;

import com.barbatech.natomada.auth.domain.entities.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for OtpToken entity
 */
@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    /**
     * Find the latest valid OTP for a phone number
     */
    @Query("SELECT ot FROM OtpToken ot WHERE ot.phoneNumber = :phoneNumber " +
           "AND ot.expiresAt > :now AND ot.verifiedAt IS NULL " +
           "ORDER BY ot.createdAt DESC LIMIT 1")
    Optional<OtpToken> findLatestValidOtpByPhoneNumber(
        @Param("phoneNumber") String phoneNumber,
        @Param("now") LocalDateTime now
    );

    /**
     * Delete all expired OTPs
     */
    @Modifying
    @Query("DELETE FROM OtpToken ot WHERE ot.expiresAt < :now")
    void deleteExpiredOtps(@Param("now") LocalDateTime now);

    /**
     * Delete all OTPs for a specific phone number
     */
    @Modifying
    @Query("DELETE FROM OtpToken ot WHERE ot.phoneNumber = :phoneNumber")
    void deleteByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
