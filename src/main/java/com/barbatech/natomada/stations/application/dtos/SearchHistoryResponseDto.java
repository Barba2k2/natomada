package com.barbatech.natomada.stations.application.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for search history response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchHistoryResponseDto {

    private Long id;
    private Long userId;
    private String searchQuery;
    private Long stationId;
    private String stationOcmId;
    private String stationName;
    private String stationAddress;
    private StationResponseDto station;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
