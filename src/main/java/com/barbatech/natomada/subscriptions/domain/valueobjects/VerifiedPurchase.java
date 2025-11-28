package com.barbatech.natomada.subscriptions.domain.valueobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Value object representing a verified in-app purchase
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifiedPurchase {

    /**
     * Product ID from the store
     */
    private String productId;

    /**
     * Transaction ID from the store
     */
    private String transactionId;

    /**
     * Original transaction ID (for renewals)
     */
    private String originalTransactionId;

    /**
     * Purchase date
     */
    private LocalDateTime purchaseDate;

    /**
     * Expiration date (for subscriptions)
     */
    private LocalDateTime expirationDate;

    /**
     * Whether the subscription is set to auto-renew
     */
    private boolean autoRenewing;

    /**
     * Platform (ios or android)
     */
    private String platform;

    /**
     * Raw receipt data for future verification
     */
    private String receiptData;

    /**
     * Whether this is a trial purchase
     */
    private boolean isTrial;

    /**
     * Whether the purchase is valid
     */
    private boolean isValid;
}
