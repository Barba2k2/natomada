package com.barbatech.natomada.subscriptions.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.subscriptions.application.dtos.SubscriptionResponseDto;
import com.barbatech.natomada.subscriptions.application.dtos.VerifyPurchaseRequestDto;
import com.barbatech.natomada.subscriptions.application.services.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for subscription and in-app purchase endpoints
 */
@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "Endpoints for subscription and premium features management")
@SecurityRequirement(name = "bearerAuth")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    /**
     * Get user subscription status
     * GET /api/subscriptions/status
     */
    @Operation(summary = "Get subscription status", description = "Returns the current subscription status for the authenticated user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Subscription status retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/status")
    public ResponseEntity<SubscriptionResponse> getSubscriptionStatus(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        SubscriptionResponseDto subscription = subscriptionService.getSubscriptionStatus(userId);

        return ResponseEntity.ok(SubscriptionResponse.builder()
            .success(true)
            .data(subscription)
            .isPremium(subscription != null && subscription.getIsActive())
            .build());
    }

    /**
     * Check if user is premium
     * GET /api/subscriptions/is-premium
     */
    @Operation(summary = "Check premium status", description = "Quick check if user has active premium subscription")
    @GetMapping("/is-premium")
    public ResponseEntity<PremiumStatusResponse> isPremium(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        boolean isPremium = subscriptionService.isPremiumUser(userId);

        return ResponseEntity.ok(PremiumStatusResponse.builder()
            .success(true)
            .isPremium(isPremium)
            .build());
    }

    /**
     * Verify and activate purchase
     * POST /api/subscriptions/verify
     */
    @Operation(summary = "Verify purchase", description = "Verifies and activates an in-app purchase")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Purchase verified and activated"),
        @ApiResponse(responseCode = "400", description = "Invalid purchase data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PostMapping("/verify")
    public ResponseEntity<SubscriptionResponse> verifyPurchase(
        Authentication authentication,
        @Valid @RequestBody VerifyPurchaseRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        SubscriptionResponseDto subscription = subscriptionService.verifyAndActivatePurchase(userId, dto);

        return ResponseEntity.ok(SubscriptionResponse.builder()
            .success(true)
            .message("Purchase verified and premium activated successfully")
            .data(subscription)
            .isPremium(subscription.getIsActive())
            .build());
    }

    /**
     * Cancel subscription
     * POST /api/subscriptions/cancel
     */
    @Operation(summary = "Cancel subscription", description = "Cancels the user's subscription (remains active until end date)")
    @PostMapping("/cancel")
    public ResponseEntity<SubscriptionResponse> cancelSubscription(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        SubscriptionResponseDto subscription = subscriptionService.cancelSubscription(userId);

        return ResponseEntity.ok(SubscriptionResponse.builder()
            .success(true)
            .message("Subscription cancelled. Premium access will remain active until " + subscription.getEndDate())
            .data(subscription)
            .isPremium(subscription.getIsActive())
            .build());
    }

    // Response wrapper classes
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class SubscriptionResponse {
        private Boolean success;
        private String message;
        private SubscriptionResponseDto data;
        private Boolean isPremium;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class PremiumStatusResponse {
        private Boolean success;
        private Boolean isPremium;
    }
}
