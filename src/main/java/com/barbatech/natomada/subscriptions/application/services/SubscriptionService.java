package com.barbatech.natomada.subscriptions.application.services;

import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.subscriptions.application.dtos.SubscriptionResponseDto;
import com.barbatech.natomada.subscriptions.application.dtos.VerifyPurchaseRequestDto;
import com.barbatech.natomada.subscriptions.domain.entities.Subscription;
import com.barbatech.natomada.subscriptions.domain.enums.SubscriptionStatus;
import com.barbatech.natomada.subscriptions.infrastructure.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for subscription operations
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

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
     * TODO: Implement actual receipt verification with Apple/Google servers
     * For now, this is a simplified version that trusts the client
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

        // TODO: Add actual receipt validation here
        // For iOS: Validate with App Store Server API
        // For Android: Validate with Google Play Developer API

        // Get or create subscription
        Subscription subscription = subscriptionRepository.findByUserId(userId)
            .orElse(Subscription.builder()
                .user(user)
                .build());

        // Update subscription details
        subscription.setProductId(dto.getProductId());
        subscription.setPlatform(dto.getPlatform());
        subscription.setStatus(SubscriptionStatus.ACTIVE);
        subscription.setOriginalTransactionId(dto.getTransactionId());
        subscription.setLatestReceiptData(dto.getPurchaseToken());
        subscription.setStartDate(LocalDateTime.now());

        // Set end date based on product (monthly = 30 days, yearly = 365 days)
        if (dto.getProductId().contains("monthly")) {
            subscription.setEndDate(LocalDateTime.now().plusDays(30));
        } else if (dto.getProductId().contains("yearly")) {
            subscription.setEndDate(LocalDateTime.now().plusDays(365));
        } else {
            subscription.setEndDate(LocalDateTime.now().plusDays(30)); // Default to monthly
        }

        subscription.setAutoRenewing(true);

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
