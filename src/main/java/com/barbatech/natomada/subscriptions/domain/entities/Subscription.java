package com.barbatech.natomada.subscriptions.domain.entities;

import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.subscriptions.domain.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Subscription entity for premium features
 */
@Entity
@Table(name = "subscriptions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private SubscriptionStatus status = SubscriptionStatus.NONE;

    @Column(length = 20)
    private String platform; // 'ios' or 'android'

    @Column(name = "original_transaction_id")
    private String originalTransactionId;

    @Column(name = "latest_receipt_data", columnDefinition = "TEXT")
    private String latestReceiptData;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "auto_renewing", nullable = false)
    @Builder.Default
    private Boolean autoRenewing = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * Check if subscription is currently active
     */
    public boolean isActive() {
        return hasAccess() && !isExpired();
    }

    /**
     * Check if user has premium access
     */
    public boolean hasAccess() {
        return status == SubscriptionStatus.ACTIVE ||
               status == SubscriptionStatus.GRACE_PERIOD ||
               status == SubscriptionStatus.CANCELLED;
    }

    /**
     * Check if subscription has expired
     */
    public boolean isExpired() {
        return endDate != null && endDate.isBefore(LocalDateTime.now());
    }

    /**
     * Get days remaining
     */
    public Long getDaysRemaining() {
        if (endDate == null) return null;
        return java.time.Duration.between(LocalDateTime.now(), endDate).toDays();
    }
}
