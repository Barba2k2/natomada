package com.barbatech.natomada.subscriptions.infrastructure.repositories;

import com.barbatech.natomada.subscriptions.domain.entities.Subscription;
import com.barbatech.natomada.subscriptions.domain.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Subscription entity
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    /**
     * Find subscription by user ID
     */
    Optional<Subscription> findByUserId(Long userId);

    /**
     * Find all active subscriptions that need renewal check
     */
    @Query("SELECT s FROM Subscription s WHERE s.status = :status AND s.endDate < :beforeDate")
    List<Subscription> findByStatusAndEndDateBefore(SubscriptionStatus status, LocalDateTime beforeDate);

    /**
     * Check if user has active subscription
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Subscription s " +
           "WHERE s.user.id = :userId AND s.status IN ('ACTIVE', 'GRACE_PERIOD', 'CANCELLED') " +
           "AND (s.endDate IS NULL OR s.endDate > CURRENT_TIMESTAMP)")
    boolean hasActiveSubscription(Long userId);

    /**
     * Delete subscription by user ID
     */
    void deleteByUserId(Long userId);
}
