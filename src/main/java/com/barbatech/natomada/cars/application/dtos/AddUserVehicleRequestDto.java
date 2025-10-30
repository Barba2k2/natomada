package com.barbatech.natomada.cars.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for adding user vehicle
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserVehicleRequestDto {

    @NotNull(message = "ID do carro é obrigatório")
    private Long carId;

    @Size(max = 100, message = "Apelido deve ter no máximo 100 caracteres")
    private String nickname;

    @Size(max = 20, message = "Placa deve ter no máximo 20 caracteres")
    private String licensePlate;

    @Size(max = 50, message = "Cor deve ter no máximo 50 caracteres")
    private String color;

    @Builder.Default
    private Boolean isPrimary = false;
}
