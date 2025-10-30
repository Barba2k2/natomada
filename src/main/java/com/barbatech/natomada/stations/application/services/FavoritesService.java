package com.barbatech.natomada.stations.application.services;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.stations.application.dtos.FavoriteResponseDto;
import com.barbatech.natomada.stations.application.dtos.StationResponseDto;
import com.barbatech.natomada.stations.domain.entities.Favorite;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.repositories.FavoriteRepository;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for favorite stations operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoriteRepository favoriteRepository;
    private final StationRepository stationRepository;
    private final UserRepository userRepository;

    /**
     * Get all favorites for a user
     */
    @Transactional(readOnly = true)
    public List<FavoriteResponseDto> getUserFavorites(Long userId) {
        log.info("Getting favorites for user: {}", userId);

        List<Favorite> favorites = favoriteRepository.findByUserIdWithStation(userId);

        return favorites.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Add station to favorites
     */
    @Transactional
    public MessageResponseDto addFavorite(Long userId, Long stationId, String notes) {
        log.info("Adding station {} to favorites for user {}", stationId, userId);

        // Check if user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Check if station exists
        Station station = stationRepository.findById(stationId)
            .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        // Check if already favorited
        if (favoriteRepository.existsByUserIdAndStationId(userId, stationId)) {
            return MessageResponseDto.builder()
                .message("Estação já está nos favoritos")
                .build();
        }

        // Create favorite
        Favorite favorite = Favorite.builder()
            .user(user)
            .station(station)
            .notes(notes)
            .build();

        favoriteRepository.save(favorite);

        log.info("Station {} added to favorites for user {}", stationId, userId);

        return MessageResponseDto.builder()
            .message("Estação adicionada aos favoritos")
            .build();
    }

    /**
     * Remove station from favorites
     */
    @Transactional
    public MessageResponseDto removeFavorite(Long userId, Long stationId) {
        log.info("Removing station {} from favorites for user {}", stationId, userId);

        // Check if favorite exists
        if (!favoriteRepository.existsByUserIdAndStationId(userId, stationId)) {
            throw new IllegalArgumentException("Estação não está nos favoritos");
        }

        favoriteRepository.deleteByUserIdAndStationId(userId, stationId);

        log.info("Station {} removed from favorites for user {}", stationId, userId);

        return MessageResponseDto.builder()
            .message("Estação removida dos favoritos")
            .build();
    }

    /**
     * Check if station is favorited by user
     */
    @Transactional(readOnly = true)
    public CheckFavoriteResponse checkIsFavorite(Long userId, Long stationId) {
        boolean isFavorite = favoriteRepository.existsByUserIdAndStationId(userId, stationId);

        return CheckFavoriteResponse.builder()
            .isFavorite(isFavorite)
            .build();
    }

    /**
     * Map Favorite entity to response DTO
     */
    private FavoriteResponseDto mapToResponse(Favorite favorite) {
        Station station = favorite.getStation();

        return FavoriteResponseDto.builder()
            .id(favorite.getId())
            .userId(favorite.getUser().getId())
            .stationId(station.getId())
            .station(mapStationToResponse(station))
            .notes(favorite.getNotes())
            .lastVisitedAt(favorite.getLastVisitedAt())
            .visitCount(favorite.getVisitCount())
            .createdAt(favorite.getCreatedAt())
            .updatedAt(favorite.getUpdatedAt())
            .build();
    }

    /**
     * Map Station entity to response DTO
     */
    private StationResponseDto mapStationToResponse(Station station) {
        return StationResponseDto.builder()
            .id(station.getId())
            .ocmId(station.getOcmId())
            .ocmUuid(station.getOcmUuid())
            .googlePlaceId(station.getGooglePlaceId())
            .externalId(station.getExternalId())
            .name(station.getName())
            .address(station.getAddress())
            .city(station.getCity())
            .state(station.getState())
            .postalCode(station.getPostalCode())
            .country(station.getCountry())
            .latitude(station.getLatitude())
            .longitude(station.getLongitude())
            .phone(station.getPhone())
            .isOperational(station.getIsOperational())
            .totalConnectors(station.getTotalConnectors())
            .connectors(station.getConnectors())
            .operator(StationResponseDto.OperatorDto.builder()
                .name(station.getOperatorName())
                .website(station.getOperatorWebsite())
                .phone(station.getOperatorPhone())
                .email(station.getOperatorEmail())
                .build())
            .usageType(StationResponseDto.UsageTypeDto.builder()
                .title(station.getUsageType())
                .requiresMembership(station.getRequiresMembership())
                .payAtLocation(station.getPayAtLocation())
                .requiresAccessKey(station.getRequiresAccessKey())
                .build())
            .usageCost(station.getUsageCost())
            .rating(StationResponseDto.RatingDto.builder()
                .ocm(station.getOcmRating())
                .ocmCount(station.getOcmReviewCount())
                .google(station.getGoogleRating())
                .googleCount(station.getGoogleReviewCount())
                .combined(station.getCombinedRating())
                .build())
            .totalReviews(station.getTotalReviews())
            .openingHours(station.getOpeningHours())
            .isOpen24h(station.getIsOpen24h())
            .lastVerifiedAt(station.getLastVerifiedAt())
            .isRecentlyVerified(station.getIsRecentlyVerified())
            .lastSyncAt(station.getLastSyncAt())
            .createdAt(station.getCreatedAt())
            .updatedAt(station.getUpdatedAt())
            .build();
    }

    /**
     * Response DTO for checking if station is favorite
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CheckFavoriteResponse {
        private Boolean isFavorite;
    }
}
