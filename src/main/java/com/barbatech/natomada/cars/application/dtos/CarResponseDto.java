package com.barbatech.natomada.cars.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for car response (includes imageUrl)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto {

    private Long id;
    private String brand;
    private String model;
    private BigDecimal batteryCapacity;
    private Integer maxSpeed;
    private Integer fastChargingPower;
    private String connector;
    private String bodyType;

    // URL da imagem do carro
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
