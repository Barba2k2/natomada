package com.barbatech.natomada.cars.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for user vehicle response (includes car with imageUrl)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVehicleResponseDto {

    private Long id;
    private Long carId;
    private String nickname;
    private String licensePlate;
    private String color;
    private Boolean isPrimary;
    private Integer totalCharges;
    private BigDecimal totalKwhCharged;
    private LocalDateTime lastChargedAt;
    private CarResponseDto car; // Inclui imageUrl do carro
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
