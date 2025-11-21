package com.barbatech.natomada.auth.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for forgot password request
 * Supports both email and SMS delivery methods
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequestDto {

    @Email(message = "Email deve ser válido")
    private String email;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Telefone inválido. Use formato internacional com DDI (ex: +5511999999999)")
    private String phoneNumber;

    private String deliveryMethod;
}
