package com.barbatech.natomada.subscriptions.domain.enums;

/**
 * Subscription status enum
 */
public enum SubscriptionStatus {
    /**
     * User has never subscribed
     */
    NONE,

    /**
     * Subscription is active
     */
    ACTIVE,

    /**
     * Subscription has expired
     */
    EXPIRED,

    /**
     * Subscription is in grace period (payment failed but still active)
     */
    GRACE_PERIOD,

    /**
     * Subscription is cancelled but still active until end date
     */
    CANCELLED
}
