package com.barbatech.natomada.subscriptions.application.dtos;

import com.barbatech.natomada.subscriptions.domain.entities.Subscription;
import com.barbatech.natomada.subscriptions.domain.enums.SubscriptionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Response DTO for subscription
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionResponseDto {

    private Long id;
    private Long userId;
    private String productId;
    private SubscriptionStatus status;
    private String platform;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean autoRenewing;
    private Boolean isActive;
    private Long daysRemaining;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Convert entity to DTO
     */
    public static SubscriptionResponseDto fromEntity(Subscription subscription) {
        if (subscription == null) {
            return null;
        }

        return SubscriptionResponseDto.builder()
            .id(subscription.getId())
            .userId(subscription.getUser().getId())
            .productId(subscription.getProductId())
            .status(subscription.getStatus())
            .platform(subscription.getPlatform())
            .startDate(subscription.getStartDate())
            .endDate(subscription.getEndDate())
            .autoRenewing(subscription.getAutoRenewing())
            .isActive(subscription.isActive())
            .daysRemaining(subscription.getDaysRemaining())
            .createdAt(subscription.getCreatedAt())
            .updatedAt(subscription.getUpdatedAt())
            .build();
    }
}
