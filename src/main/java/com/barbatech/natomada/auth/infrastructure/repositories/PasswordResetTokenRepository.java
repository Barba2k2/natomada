package com.barbatech.natomada.auth.infrastructure.repositories;

import com.barbatech.natomada.auth.domain.entities.PasswordResetToken;
import com.barbatech.natomada.auth.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Repository for PasswordResetToken entity
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Find a password reset token by token string
     */
    Optional<PasswordResetToken> findByToken(String token);

    /**
     * Find the latest valid token for a user by email
     */
    @Query("SELECT prt FROM PasswordResetToken prt WHERE prt.email = :email " +
           "AND prt.expiresAt > :now AND prt.usedAt IS NULL " +
           "ORDER BY prt.createdAt DESC LIMIT 1")
    Optional<PasswordResetToken> findLatestValidTokenByEmail(
        @Param("email") String email,
        @Param("now") LocalDateTime now
    );

    /**
     * Delete all expired tokens
     */
    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") LocalDateTime now);

    /**
     * Delete all tokens for a specific user
     */
    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.user = :user")
    void deleteByUser(@Param("user") User user);

    /**
     * Delete all tokens for a specific email
     */
    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.email = :email")
    void deleteByEmail(@Param("email") String email);
}
