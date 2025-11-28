package com.barbatech.natomada.subscriptions.infrastructure.services;

import com.barbatech.natomada.subscriptions.application.dtos.VerifyPurchaseRequestDto;
import com.barbatech.natomada.subscriptions.application.exceptions.InvalidReceiptException;
import com.barbatech.natomada.subscriptions.application.exceptions.ReceiptVerificationException;
import com.barbatech.natomada.subscriptions.application.services.ReceiptVerificationService;
import com.barbatech.natomada.subscriptions.domain.valueobjects.VerifiedPurchase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

/**
 * Google Play receipt verification service
 * Uses Google Play Developer API to verify purchases
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GooglePlayReceiptVerificationService implements ReceiptVerificationService {

    private final RestTemplate restTemplate;

    @Value("${google.play.package-name:com.barbatech.natomada}")
    private String packageName;

    @Value("${google.play.enabled:false}")
    private boolean enabled;

    /**
     * Verify Google Play purchase
     *
     * Documentation: https://developers.google.com/android-publisher/api-ref/rest/v3/purchases.subscriptions
     */
    @Override
    public VerifiedPurchase verifyPurchase(VerifyPurchaseRequestDto dto) {
        if (!enabled) {
            log.warn("Google Play verification is disabled. Accepting purchase without verification.");
            return createMockVerifiedPurchase(dto);
        }

        try {
            log.info("Verifying Google Play purchase - Product: {}, Token: {}",
                    dto.getProductId(), maskToken(dto.getPurchaseToken()));

            // TODO: Implement actual Google Play API call
            // Requires:
            // 1. Service account credentials from Google Cloud Console
            // 2. Google Play Developer API enabled
            // 3. OAuth 2.0 token for authentication
            //
            // API endpoint:
            // GET https://androidpublisher.googleapis.com/androidpublisher/v3/applications/{packageName}/purchases/subscriptions/{subscriptionId}/tokens/{token}
            //
            // For now, return mock data
            log.warn("Google Play API integration not yet implemented. Using mock verification.");
            return createMockVerifiedPurchase(dto);

        } catch (RestClientException e) {
            log.error("Failed to verify Google Play receipt", e);
            throw new ReceiptVerificationException("Failed to verify purchase with Google Play", e);
        }
    }

    @Override
    public boolean supports(String platform) {
        return "android".equalsIgnoreCase(platform);
    }

    /**
     * Create mock verified purchase for development
     */
    private VerifiedPurchase createMockVerifiedPurchase(VerifyPurchaseRequestDto dto) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusDays(30); // Default 30 days

        if (dto.getProductId() != null && dto.getProductId().contains("yearly")) {
            expiration = now.plusYears(1);
        }

        return VerifiedPurchase.builder()
                .productId(dto.getProductId())
                .transactionId(dto.getTransactionId())
                .originalTransactionId(dto.getTransactionId())
                .purchaseDate(now)
                .expirationDate(expiration)
                .autoRenewing(true)
                .platform("android")
                .receiptData(dto.getPurchaseToken())
                .isTrial(false)
                .isValid(true)
                .build();
    }

    private String maskToken(String token) {
        if (token == null || token.length() < 10) {
            return "***";
        }
        return token.substring(0, 5) + "..." + token.substring(token.length() - 5);
    }
}
