package com.barbatech.natomada.auth.application.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for validating password reset token
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateResetTokenRequestDto {

    @NotBlank(message = "Token é obrigatório")
    private String token;
}
