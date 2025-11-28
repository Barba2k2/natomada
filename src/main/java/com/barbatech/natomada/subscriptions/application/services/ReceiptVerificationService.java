package com.barbatech.natomada.subscriptions.application.services;

import com.barbatech.natomada.subscriptions.application.dtos.VerifyPurchaseRequestDto;
import com.barbatech.natomada.subscriptions.domain.valueobjects.VerifiedPurchase;

/**
 * Service for verifying in-app purchase receipts with Apple and Google
 */
public interface ReceiptVerificationService {

    /**
     * Verify purchase receipt with store backend
     *
     * @param dto Purchase verification request
     * @return Verified purchase data with expiration dates
     * @throws InvalidReceiptException if receipt is invalid
     * @throws ReceiptVerificationException if verification fails
     */
    VerifiedPurchase verifyPurchase(VerifyPurchaseRequestDto dto);

    /**
     * Check if this service supports the given platform
     */
    boolean supports(String platform);
}
