package com.barbatech.natomada.reviews.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.reviews.application.dtos.CreateReviewRequestDto;
import com.barbatech.natomada.reviews.application.dtos.ReviewResponseDto;
import com.barbatech.natomada.reviews.application.dtos.UpdateReviewRequestDto;
import com.barbatech.natomada.reviews.application.services.ReviewService;
import com.barbatech.natomada.stations.application.services.StationsService;
import com.barbatech.natomada.stations.domain.entities.Station;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for review endpoints
 */
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Endpoints para gerenciamento de avaliações de estações")
@SecurityRequirement(name = "bearerAuth")
public class ReviewsController {

    private final ReviewService reviewService;
    private final StationsService stationsService;

    /**
     * Submit a new review
     * POST /api/reviews/station/{stationId}
     */
    @Operation(summary = "Enviar avaliação", description = "Cria uma nova avaliação para uma estação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Avaliação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Estação não encontrada"),
        @ApiResponse(responseCode = "409", description = "Usuário já avaliou esta estação")
    })
    @PostMapping("/station/{stationId}")
    public ResponseEntity<ReviewResponse> submitReview(
        Authentication authentication,
        @Parameter(description = "ID da estação (pode ser ocm_123 ou ID numérico)", required = true) @PathVariable String stationId,
        @Valid @RequestBody CreateReviewRequestDto request
    ) {
        Long userId = Long.parseLong(authentication.getName());
        Long internalStationId = resolveStationId(stationId);
        ReviewResponseDto review = reviewService.submitReview(userId, internalStationId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(ReviewResponse.builder()
            .success(true)
            .data(review)
            .build());
    }

    /**
     * Get reviews for a station
     * GET /api/reviews/station/{stationId}
     */
    @Operation(summary = "Listar avaliações de estação", description = "Retorna todas as avaliações de uma estação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliações recuperadas com sucesso"),
        @ApiResponse(responseCode = "404", description = "Estação não encontrada")
    })
    @GetMapping("/station/{stationId}")
    public ResponseEntity<ReviewListResponse> getStationReviews(
        @Parameter(description = "ID da estação", required = true) @PathVariable Long stationId,
        @Parameter(description = "Limite de resultados") @RequestParam(required = false) Integer limit,
        @Parameter(description = "Offset para paginação") @RequestParam(required = false) Integer offset
    ) {
        List<ReviewResponseDto> reviews = reviewService.getStationReviews(stationId, limit, offset);

        return ResponseEntity.ok(ReviewListResponse.builder()
            .success(true)
            .data(reviews)
            .build());
    }

    /**
     * Get reviews by user
     * GET /api/reviews/user/me
     */
    @Operation(summary = "Listar minhas avaliações", description = "Retorna todas as avaliações do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliações recuperadas com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping("/user/me")
    public ResponseEntity<ReviewListResponse> getUserReviews(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        List<ReviewResponseDto> reviews = reviewService.getUserReviews(userId);

        return ResponseEntity.ok(ReviewListResponse.builder()
            .success(true)
            .data(reviews)
            .build());
    }

    /**
     * Update a review
     * PUT /api/reviews/{reviewId}
     */
    @Operation(summary = "Atualizar avaliação", description = "Atualiza uma avaliação existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para editar esta avaliação"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @PutMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
        Authentication authentication,
        @Parameter(description = "ID da avaliação", required = true) @PathVariable Long reviewId,
        @Valid @RequestBody UpdateReviewRequestDto request
    ) {
        Long userId = Long.parseLong(authentication.getName());
        ReviewResponseDto review = reviewService.updateReview(userId, reviewId, request);

        return ResponseEntity.ok(ReviewResponse.builder()
            .success(true)
            .data(review)
            .build());
    }

    /**
     * Delete a review
     * DELETE /api/reviews/{reviewId}
     */
    @Operation(summary = "Deletar avaliação", description = "Remove uma avaliação existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avaliação deletada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "403", description = "Sem permissão para deletar esta avaliação"),
        @ApiResponse(responseCode = "404", description = "Avaliação não encontrada")
    })
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<MessageResponseDto> deleteReview(
        Authentication authentication,
        @Parameter(description = "ID da avaliação", required = true) @PathVariable Long reviewId
    ) {
        Long userId = Long.parseLong(authentication.getName());
        reviewService.deleteReview(userId, reviewId);

        return ResponseEntity.ok(MessageResponseDto.builder()
            .message("Avaliação deletada com sucesso")
            .build());
    }

    /**
     * Resolve station ID from OCM format or numeric ID
     *
     * @param stationId Station ID (can be "ocm_123" or "123")
     * @return Internal database station ID
     */
    private Long resolveStationId(String stationId) {
        try {
            // Try to parse as numeric ID
            return Long.parseLong(stationId);
        } catch (NumberFormatException e) {
            // It's an OCM ID, fetch/create station and get internal ID
            Station station = stationsService.getOrFetchStationByOcmId(stationId);
            return station.getId();
        }
    }

    // Response wrapper classes
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewResponse {
        private boolean success;
        private ReviewResponseDto data;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListResponse {
        private boolean success;
        private List<ReviewResponseDto> data;
    }
}
