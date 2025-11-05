package com.barbatech.natomada.stations.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.stations.application.dtos.AddFavoriteRequestDto;
import com.barbatech.natomada.stations.application.dtos.FavoriteResponseDto;
import com.barbatech.natomada.stations.application.services.FavoritesService;
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
 * Controller for favorites endpoints
 */
@RestController
@RequestMapping("/api/stations")
@RequiredArgsConstructor
@Tag(name = "Favorites", description = "Endpoints para gerenciamento de estações favoritas do usuário")
@SecurityRequirement(name = "bearerAuth")
public class FavoritesController {

    private final FavoritesService favoritesService;

    /**
     * Get all user favorites
     * GET /api/stations/favorites
     */
    @Operation(summary = "Listar favoritos", description = "Retorna todas as estações favoritas do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favoritos recuperados com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/favorites")
    public ResponseEntity<FavoritesResponse> getUserFavorites(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<FavoriteResponseDto> favorites = favoritesService.getUserFavorites(userId);

        return ResponseEntity.ok(FavoritesResponse.builder()
            .success(true)
            .data(favorites)
            .build());
    }

    /**
     * Add station to favorites
     * POST /api/stations/{id}/favorite
     */
    @Operation(summary = "Adicionar favorito", description = "Adiciona uma estação aos favoritos do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Estação adicionada aos favoritos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "409", description = "Estação já está nos favoritos")
    })
    @PostMapping("/{id}/favorite")
    public ResponseEntity<MessageResponseDto> addFavorite(
        Authentication authentication,
        @Parameter(description = "ID da estação", required = true) @PathVariable Long id,
        @Valid @RequestBody(required = false) AddFavoriteRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        String notes = dto != null ? dto.getNotes() : null;

        MessageResponseDto response = favoritesService.addFavorite(userId, id, notes);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Remove station from favorites
     * DELETE /api/stations/{id}/unfavorite
     */
    @Operation(summary = "Remover favorito", description = "Remove uma estação dos favoritos do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estação removida dos favoritos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Favorito não encontrado")
    })
    @DeleteMapping("/{id}/unfavorite")
    public ResponseEntity<MessageResponseDto> removeFavorite(
        Authentication authentication,
        @Parameter(description = "ID da estação", required = true) @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = favoritesService.removeFavorite(userId, id);

        return ResponseEntity.ok(response);
    }

    /**
     * Check if station is favorited
     * GET /api/stations/{id}/is-favorite
     */
    @GetMapping("/{id}/is-favorite")
    public ResponseEntity<FavoritesService.CheckFavoriteResponse> checkIsFavorite(
        Authentication authentication,
        @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        FavoritesService.CheckFavoriteResponse response = favoritesService.checkIsFavorite(userId, id);

        return ResponseEntity.ok(response);
    }

    // Response wrapper class
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class FavoritesResponse {
        private Boolean success;
        private List<FavoriteResponseDto> data;
    }
}
