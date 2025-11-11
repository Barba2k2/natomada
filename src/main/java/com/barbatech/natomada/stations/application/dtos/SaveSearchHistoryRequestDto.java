package com.barbatech.natomada.stations.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for saving search history request
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveSearchHistoryRequestDto {

    @NotBlank(message = "Search query is required")
    @Size(max = 500, message = "Search query must be less than 500 characters")
    private String searchQuery;

    private Long stationId;

    private String stationOcmId;

    private String stationName;

    @Size(max = 500, message = "Station address must be less than 500 characters")
    private String stationAddress;
}
