package com.barbatech.natomada.cars.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for adding user vehicle
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddUserVehicleRequestDto {

    @NotBlank(message = "Marca é obrigatória")
    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    private String brand;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(max = 100, message = "Modelo deve ter no máximo 100 caracteres")
    private String model;

    @NotBlank(message = "Ano é obrigatório")
    @Size(max = 10, message = "Ano deve ter no máximo 10 caracteres")
    private String year;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(max = 50, message = "Tipo deve ter no máximo 50 caracteres")
    private String type;

    @Size(max = 100, message = "Apelido deve ter no máximo 100 caracteres")
    private String nickname;

    @Size(max = 20, message = "Placa deve ter no máximo 20 caracteres")
    private String licensePlate;

    @Size(max = 50, message = "Cor deve ter no máximo 50 caracteres")
    private String color;

    @Size(max = 500, message = "URL da imagem deve ter no máximo 500 caracteres")
    private String imageUrl;

    @Builder.Default
    private Boolean isPrimary = false;

    private Double batteryCapacity;

    private List<String> connectorTypes;

    private Integer maxChargingSpeed;

    private Integer maxSpeed;
}
