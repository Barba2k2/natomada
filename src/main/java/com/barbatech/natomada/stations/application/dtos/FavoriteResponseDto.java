package com.barbatech.natomada.stations.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for favorite response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponseDto {

    private Long id;
    private Long userId;
    private Long stationId;
    private StationResponseDto station;
    private String notes;
    private LocalDateTime lastVisitedAt;
    private Integer visitCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
