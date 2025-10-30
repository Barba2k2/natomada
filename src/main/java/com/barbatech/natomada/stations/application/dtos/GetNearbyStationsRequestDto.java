package com.barbatech.natomada.stations.application.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for getting nearby stations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetNearbyStationsRequestDto {

    @NotNull(message = "Latitude é obrigatória")
    @DecimalMin(value = "-90.0", message = "Latitude deve estar entre -90 e 90")
    @DecimalMax(value = "90.0", message = "Latitude deve estar entre -90 e 90")
    private Double latitude;

    @NotNull(message = "Longitude é obrigatória")
    @DecimalMin(value = "-180.0", message = "Longitude deve estar entre -180 e 180")
    @DecimalMax(value = "180.0", message = "Longitude deve estar entre -180 e 180")
    private Double longitude;

    @Min(value = 100, message = "Raio mínimo é 100 metros")
    @Max(value = 50000, message = "Raio máximo é 50km")
    private Integer radius = 5000;

    @Min(value = 1, message = "Limite mínimo é 1")
    @Max(value = 100, message = "Limite máximo é 100")
    private Integer limit = 20;
}
