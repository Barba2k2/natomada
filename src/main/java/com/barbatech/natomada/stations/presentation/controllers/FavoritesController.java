package com.barbatech.natomada.stations.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.stations.application.dtos.AddFavoriteRequestDto;
import com.barbatech.natomada.stations.application.dtos.FavoriteResponseDto;
import com.barbatech.natomada.stations.application.services.FavoritesService;
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
public class FavoritesController {

    private final FavoritesService favoritesService;

    /**
     * Get all user favorites
     * GET /api/stations/favorites
     */
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
    @PostMapping("/{id}/favorite")
    public ResponseEntity<MessageResponseDto> addFavorite(
        Authentication authentication,
        @PathVariable Long id,
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
    @DeleteMapping("/{id}/unfavorite")
    public ResponseEntity<MessageResponseDto> removeFavorite(
        Authentication authentication,
        @PathVariable Long id
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
