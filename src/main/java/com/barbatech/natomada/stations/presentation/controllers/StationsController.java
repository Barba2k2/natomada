package com.barbatech.natomada.stations.presentation.controllers;

import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.application.services.StationsService;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for stations endpoints
 */
@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
public class StationsController {

    private final StationsService stationsService;

    /**
     * Get nearby stations
     * GET /api/stations/nearby?latitude=-23.5629&longitude=-46.6544&radius=5000&limit=20
     */
    @GetMapping("/nearby")
    public ResponseEntity<NearbyStationsResponse> getNearbyStations(
        @RequestParam @NotNull(message = "Latitude é obrigatória")
        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") Double latitude,

        @RequestParam @NotNull(message = "Longitude é obrigatória")
        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") Double longitude,

        @RequestParam(required = false, defaultValue = "5000")
        @Min(value = 100) @Max(value = 50000) Integer radius,

        @RequestParam(required = false, defaultValue = "20")
        @Min(value = 1) @Max(value = 100) Integer limit
    ) {
        List<StationResponseDto> stations = stationsService.getNearbyStations(
            latitude, longitude, radius, limit
        );

        return ResponseEntity.ok(NearbyStationsResponse.builder()
            .data(stations)
            .meta(NearbyStationsResponse.MetaDto.builder()
                .total(stations.size())
                .latitude(latitude)
                .longitude(longitude)
                .radius(radius)
                .sources(NearbyStationsResponse.SourcesDto.builder()
                    .primary("OpenChargeMap")
                    .enrichment("Google Places")
                    .build())
                .build())
            .build());
    }

    // Response wrapper classes
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class NearbyStationsResponse {
        private List<StationResponseDto> data;
        private MetaDto meta;

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class MetaDto {
            private Integer total;
            private Double latitude;
            private Double longitude;
            private Integer radius;
            private SourcesDto sources;
        }

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class SourcesDto {
            private String primary;
            private String enrichment;
        }
    }
}
