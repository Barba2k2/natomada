package com.barbatech.natomada.profile.application.dtos;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating user profile
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequestDto {

    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String name;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Telefone inválido. Use formato internacional com DDI (ex: +5511999999999)")
    private String phone;

    @Pattern(regexp = "^(Male|Female)$", message = "Gênero deve ser Male ou Female")
    private String gender;
}
