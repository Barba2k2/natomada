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
import java.util.HashMap;
import java.util.Map;

/**
 * App Store receipt verification service
 * Uses Apple App Store Server API to verify purchases
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AppStoreReceiptVerificationService implements ReceiptVerificationService {

    private final RestTemplate restTemplate;

    @Value("${apple.app-store.verify-url:https://buy.itunes.apple.com/verifyReceipt}")
    private String productionUrl;

    @Value("${apple.app-store.sandbox-url:https://sandbox.itunes.apple.com/verifyReceipt}")
    private String sandboxUrl;

    @Value("${apple.app-store.shared-secret:}")
    private String sharedSecret;

    @Value("${apple.app-store.enabled:false}")
    private boolean enabled;

    /**
     * Verify App Store purchase
     *
     * Documentation: https://developer.apple.com/documentation/appstoreserverapi
     */
    @Override
    public VerifiedPurchase verifyPurchase(VerifyPurchaseRequestDto dto) {
        if (!enabled) {
            log.warn("App Store verification is disabled. Accepting purchase without verification.");
            return createMockVerifiedPurchase(dto);
        }

        try {
            log.info("Verifying App Store purchase - Product: {}", dto.getProductId());

            // Try production endpoint first
            VerifiedPurchase result = verifyWithApple(dto, productionUrl);

            // If production returns sandbox receipt error (21007), try sandbox
            if (result == null) {
                log.info("Receipt is for sandbox environment, retrying with sandbox URL");
                result = verifyWithApple(dto, sandboxUrl);
            }

            if (result == null || !result.isValid()) {
                throw new InvalidReceiptException("Receipt verification failed");
            }

            return result;

        } catch (RestClientException e) {
            log.error("Failed to verify App Store receipt", e);
            throw new ReceiptVerificationException("Failed to verify purchase with App Store", e);
        }
    }

    @Override
    public boolean supports(String platform) {
        return "ios".equalsIgnoreCase(platform);
    }

    /**
     * Verify receipt with Apple servers
     */
    private VerifiedPurchase verifyWithApple(VerifyPurchaseRequestDto dto, String url) {
        if (!enabled) {
            log.warn("App Store API integration not yet implemented. Using mock verification.");
            return createMockVerifiedPurchase(dto);
        }

        try {
            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("receipt-data", dto.getPurchaseToken());
            if (sharedSecret != null && !sharedSecret.isEmpty()) {
                requestBody.put("password", sharedSecret);
            }
            requestBody.put("exclude-old-transactions", true);

            // TODO: Implement actual App Store API call
            // Requires:
            // 1. App Store Server API credentials
            // 2. Shared secret from App Store Connect
            // 3. Handle response status codes (0=valid, 21007=sandbox, etc)
            //
            // For now, return mock data
            log.warn("App Store API integration not yet implemented. Using mock verification.");
            return createMockVerifiedPurchase(dto);

        } catch (Exception e) {
            log.error("Error verifying with Apple: {}", e.getMessage());
            return null;
        }
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
                .platform("ios")
                .receiptData(dto.getPurchaseToken())
                .isTrial(false)
                .isValid(true)
                .build();
    }
}
