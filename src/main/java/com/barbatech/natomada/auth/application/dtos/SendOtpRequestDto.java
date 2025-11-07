package com.barbatech.natomada.auth.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for send OTP request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendOtpRequestDto {

    @NotBlank(message = "Número de telefone é obrigatório")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de telefone inválido")
    private String phoneNumber;
}
