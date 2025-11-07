package com.barbatech.natomada.auth.application.dtos;

import com.barbatech.natomada.auth.domain.enums.OtpDeliveryMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Método de entrega é obrigatório")
    private OtpDeliveryMethod deliveryMethod;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de telefone inválido")
    private String phoneNumber;

    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Código OTP é obrigatório")
    @Size(min = 6, max = 6, message = "Código OTP deve ter 6 dígitos")
    @Pattern(regexp = "^\\d{6}$", message = "Código OTP deve conter apenas números")
    private String code;
}
