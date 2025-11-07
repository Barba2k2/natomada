package com.barbatech.natomada.auth.application.dtos;

import com.barbatech.natomada.auth.domain.enums.OtpDeliveryMethod;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Método de entrega é obrigatório")
    private OtpDeliveryMethod deliveryMethod;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Número de telefone inválido")
    private String phoneNumber;

    @Email(message = "Email inválido")
    private String email;
}
