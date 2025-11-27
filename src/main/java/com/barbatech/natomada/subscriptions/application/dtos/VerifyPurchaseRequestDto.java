package com.barbatech.natomada.subscriptions.application.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request DTO for verifying in-app purchase
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyPurchaseRequestDto {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @NotBlank(message = "Platform is required")
    private String platform; // 'ios' or 'android'

    @NotBlank(message = "Purchase token/receipt is required")
    private String purchaseToken; // Receipt data for iOS, purchase token for Android

    private String transactionId; // Transaction ID for tracking
}
