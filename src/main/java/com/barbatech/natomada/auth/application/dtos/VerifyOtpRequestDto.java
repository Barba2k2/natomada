package com.barbatech.natomada.auth.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for verify OTP request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerifyOtpRequestDto {

    @NotBlank(message = "Número de telefone é obrigatório")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de telefone inválido")
    private String phoneNumber;

    @NotBlank(message = "Código OTP é obrigatório")
    @Size(min = 6, max = 6, message = "Código OTP deve ter 6 dígitos")
    @Pattern(regexp = "^\\d{6}$", message = "Código OTP deve conter apenas números")
    private String code;
}
