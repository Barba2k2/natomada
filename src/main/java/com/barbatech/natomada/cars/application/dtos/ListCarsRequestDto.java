package com.barbatech.natomada.cars.application.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for listing cars with filters
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListCarsRequestDto {

    private String brand;
    private String bodyType;
    private String search;

    @Min(value = 0, message = "Potência mínima deve ser maior ou igual a 0")
    private Integer minPower;

    private String connector;

    @Min(value = 1, message = "Página deve ser maior ou igual a 1")
    @Builder.Default
    private Integer page = 1;

    @Min(value = 1, message = "Limite deve ser maior ou igual a 1")
    @Builder.Default
    private Integer limit = 20;

    @Builder.Default
    private String sortBy = "brand";

    @Builder.Default
    private String sortOrder = "asc";
}
