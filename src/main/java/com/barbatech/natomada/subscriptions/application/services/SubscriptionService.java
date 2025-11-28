package com.barbatech.natomada.subscriptions.application.services;

import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.subscriptions.application.dtos.SubscriptionResponseDto;
import com.barbatech.natomada.subscriptions.application.dtos.VerifyPurchaseRequestDto;
import com.barbatech.natomada.subscriptions.application.exceptions.InvalidReceiptException;
import com.barbatech.natomada.subscriptions.domain.entities.Subscription;
import com.barbatech.natomada.subscriptions.domain.enums.SubscriptionStatus;
import com.barbatech.natomada.subscriptions.domain.valueobjects.VerifiedPurchase;
import com.barbatech.natomada.subscriptions.infrastructure.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for subscription operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final List<ReceiptVerificationService> verificationServices;

    /**
     * Get user subscription status
     */
    @Transactional(readOnly = true)
    public SubscriptionResponseDto getSubscriptionStatus(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        Subscription subscription = subscriptionRepository.findByUserId(userId)
            .orElse(null);

        return SubscriptionResponseDto.fromEntity(subscription);
    }

    /**
     * Check if user has premium access
     */
    @Transactional(readOnly = true)
    public boolean isPremiumUser(Long userId) {
        return subscriptionRepository.hasActiveSubscription(userId);
    }

    /**
     * Verify and activate purchase
     *
     * Verifies receipt with Apple App Store or Google Play Store
     * and activates premium subscription for the user
     */
    @Transactional
    public SubscriptionResponseDto verifyAndActivatePurchase(
        Long userId,
        VerifyPurchaseRequestDto dto
    ) {
        log.info("Verifying purchase for user {} - Product: {}, Platform: {}",
                 userId, dto.getProductId(), dto.getPlatform());

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Find appropriate verification service for platform
        ReceiptVerificationService verificationService = verificationServices.stream()
            .filter(service -> service.supports(dto.getPlatform()))
            .findFirst()
            .orElseThrow(() -> new InvalidReceiptException(
                "No verification service available for platform: " + dto.getPlatform()
            ));

        // Verify purchase with store backend (Apple/Google)
        VerifiedPurchase verifiedPurchase = verificationService.verifyPurchase(dto);

        if (!verifiedPurchase.isValid()) {
            throw new InvalidReceiptException("Purchase verification failed - receipt is invalid");
        }

        log.info("Purchase verified successfully - Transaction: {}, Expires: {}",
                verifiedPurchase.getTransactionId(),
                verifiedPurchase.getExpirationDate());

        // Get or create subscription
        Subscription subscription = subscriptionRepository.findByUserId(userId)
            .orElse(Subscription.builder()
                .user(user)
                .build());

        // Update subscription with verified data
        subscription.setProductId(verifiedPurchase.getProductId());
        subscription.setPlatform(verifiedPurchase.getPlatform());
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setOriginalTransactionId(verifiedPurchase.getOriginalTransactionId());
        subscription.setLatestReceiptData(verifiedPurchase.getReceiptData());
        subscription.setStartDate(verifiedPurchase.getPurchaseDate());
        subscription.setEndDate(verifiedPurchase.getExpirationDate());
        subscription.setAutoRenewing(verifiedPurchase.isAutoRenewing());

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        log.info("Subscription activated for user {} - Status: {}, End date: {}",
                 userId, savedSubscription.getStatus(), savedSubscription.getEndDate());

        return SubscriptionResponseDto.fromEntity(savedSubscription);
    }

    /**
     * Cancel subscription (set status to CANCELLED but keep active until end date)
     */
    @Transactional
    public SubscriptionResponseDto cancelSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalStateException("No active subscription found"));

        subscription.setStatus(SubscriptionStatus.CANCELLED);
        subscription.setAutoRenewing(false);

        Subscription savedSubscription = subscriptionRepository.save(subscription);

        log.info("Subscription cancelled for user {} - Will remain active until {}",
                 userId, savedSubscription.getEndDate());

        return SubscriptionResponseDto.fromEntity(savedSubscription);
    }
}
