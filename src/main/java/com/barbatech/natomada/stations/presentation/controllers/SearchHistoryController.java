package com.barbatech.natomada.stations.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.stations.application.dtos.SaveSearchHistoryRequestDto;
import com.barbatech.natomada.stations.application.dtos.SearchHistoryResponseDto;
import com.barbatech.natomada.stations.application.services.SearchHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for search history endpoints
 */
@RestController
@RequestMapping("/api/stations/search-history")
@RequiredArgsConstructor
@Tag(name = "Search History", description = "Endpoints para gerenciamento de histórico de buscas de estações")
@SecurityRequirement(name = "bearerAuth")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    /**
     * Save a search to history
     * POST /api/stations/search-history
     */
    @Operation(
        summary = "Salvar busca no histórico",
        description = "Salva uma busca no histórico do usuário autenticado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Busca salva com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    public ResponseEntity<SearchHistoryResponse> saveSearch(
        Authentication authentication,
        @Valid @RequestBody SaveSearchHistoryRequestDto request
    ) {
        Long userId = Long.parseLong(authentication.getName());
        SearchHistoryResponseDto response = searchHistoryService.saveSearch(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(SearchHistoryResponse.builder()
                .success(true)
                .data(response)
                .build());
    }

    /**
     * Get recent search history
     * GET /api/stations/search-history
     */
    @Operation(
        summary = "Obter histórico de buscas",
        description = "Retorna o histórico recente de buscas do usuário autenticado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Histórico recuperado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping
    public ResponseEntity<SearchHistoryListResponse> getRecentSearches(
        Authentication authentication,
        @Parameter(description = "Limite de resultados (padrão: 10)", example = "10")
        @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        Long userId = Long.parseLong(authentication.getName());
        List<SearchHistoryResponseDto> history = searchHistoryService.getRecentSearches(userId, limit);

        return ResponseEntity.ok(SearchHistoryListResponse.builder()
            .success(true)
            .data(history)
            .meta(MetaDto.builder()
                .total(history.size())
                .limit(limit)
                .build())
            .build());
    }

    /**
     * Delete specific search history entry
     * DELETE /api/stations/search-history/{id}
     */
    @Operation(
        summary = "Deletar busca do histórico",
        description = "Remove uma busca específica do histórico do usuário"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busca deletada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Busca não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteSearch(
        Authentication authentication,
        @Parameter(description = "ID da busca no histórico", required = true) @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = searchHistoryService.deleteSearch(userId, id);

        return ResponseEntity.ok(response);
    }

    /**
     * Clear all search history
     * DELETE /api/stations/search-history
     */
    @Operation(
        summary = "Limpar todo o histórico",
        description = "Remove todas as buscas do histórico do usuário autenticado"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Histórico limpo com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @DeleteMapping
    public ResponseEntity<MessageResponseDto> clearAllHistory(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = searchHistoryService.clearAllHistory(userId);

        return ResponseEntity.ok(response);
    }

    // Response wrapper classes
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class SearchHistoryResponse {
        private Boolean success;
        private SearchHistoryResponseDto data;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class SearchHistoryListResponse {
        private Boolean success;
        private List<SearchHistoryResponseDto> data;
        private MetaDto meta;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class MetaDto {
        private Integer total;
        private Integer limit;
    }
}
