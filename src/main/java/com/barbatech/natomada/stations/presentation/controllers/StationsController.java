package com.barbatech.natomada.stations.presentation.controllers;

import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.application.services.StationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Stations", description = "Endpoints para busca e consulta de estações de recarga")
public class StationsController {

    private final StationsService stationsService;

    /**
     * Get station by ID
     * GET /api/stations/{id}
     */
    @Operation(
        summary = "Buscar estação por ID",
        description = "Retorna detalhes completos de uma estação específica, incluindo fotos e avaliações do Google Places"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estação encontrada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StationDetailResponse> getStationById(
        @Parameter(description = "ID da estação (pode ser ocm_123 ou external ID)", example = "ocm_217270", required = true)
        @PathVariable String id
    ) {
        StationResponseDto station = stationsService.getStationById(id);

        return ResponseEntity.ok(StationDetailResponse.builder()
            .data(station)
            .build());
    }

    /**
     * Get nearby stations
     * GET /api/stations/nearby?latitude=-23.5629&longitude=-46.6544&radius=5000&limit=20
     */
    @Operation(
        summary = "Buscar estações próximas",
        description = "Retorna estações de recarga próximas a uma localização específica, com dados do OpenChargeMap enriquecidos com Google Places"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estações encontradas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros de busca inválidos")
    })
    @GetMapping("/nearby")
    public ResponseEntity<NearbyStationsResponse> getNearbyStations(
        @Parameter(description = "Latitude da localização de busca", example = "-23.5629", required = true)
        @RequestParam @NotNull(message = "Latitude é obrigatória")
        @DecimalMin(value = "-90.0") @DecimalMax(value = "90.0") Double latitude,

        @Parameter(description = "Longitude da localização de busca", example = "-46.6544", required = true)
        @RequestParam @NotNull(message = "Longitude é obrigatória")
        @DecimalMin(value = "-180.0") @DecimalMax(value = "180.0") Double longitude,

        @Parameter(description = "Raio de busca em metros", example = "5000")
        @RequestParam(required = false, defaultValue = "5000")
        @Min(value = 100) @Max(value = 50000) Integer radius,

        @Parameter(description = "Limite de resultados", example = "20")
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
    public static class StationDetailResponse {
        private StationResponseDto data;
    }

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
