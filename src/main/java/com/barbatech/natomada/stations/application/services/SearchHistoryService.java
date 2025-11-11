package com.barbatech.natomada.stations.application.services;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.stations.application.dtos.SaveSearchHistoryRequestDto;
import com.barbatech.natomada.stations.application.dtos.SearchHistoryResponseDto;
import com.barbatech.natomada.stations.domain.entities.SearchHistory;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.repositories.SearchHistoryRepository;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for search history operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchHistoryRepository searchHistoryRepository;
    private final StationRepository stationRepository;
    private final UserRepository userRepository;

    private static final int DEFAULT_HISTORY_LIMIT = 10;

    /**
     * Save a search to history
     */
    @Transactional
    public SearchHistoryResponseDto saveSearch(Long userId, SaveSearchHistoryRequestDto request) {
        log.info("Saving search history for user {}: query={}, stationId={}",
            userId, request.getSearchQuery(), request.getStationId());

        // Validate user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Find station if stationId is provided
        Station station = null;
        if (request.getStationId() != null) {
            log.info("Looking up station with ID: {}", request.getStationId());
            station = stationRepository.findById(request.getStationId())
                .orElse(null);
            if (station != null) {
                log.info("Found station: {} (ID: {})", station.getName(), station.getId());
            } else {
                log.warn("Station with ID {} not found", request.getStationId());
            }
        }

        // Create search history entry
        SearchHistory searchHistory = SearchHistory.builder()
            .user(user)
            .searchQuery(request.getSearchQuery())
            .station(station)
            .stationOcmId(request.getStationOcmId())
            .stationName(request.getStationName())
            .stationAddress(request.getStationAddress())
            .build();

        searchHistory = searchHistoryRepository.save(searchHistory);

        log.info("Search history saved with id: {}, stationId: {}",
            searchHistory.getId(),
            station != null ? station.getId() : null);

        return mapToResponse(searchHistory);
    }

    /**
     * Get recent search history for a user
     */
    @Transactional(readOnly = true)
    public List<SearchHistoryResponseDto> getRecentSearches(Long userId, Integer limit) {
        log.info("Getting recent searches for user {}, limit={}", userId, limit);

        int searchLimit = limit != null && limit > 0 ? limit : DEFAULT_HISTORY_LIMIT;
        Pageable pageable = PageRequest.of(0, searchLimit);

        List<SearchHistory> history = searchHistoryRepository
            .findByUserIdOrderByCreatedAtDesc(userId, pageable);

        return history.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Delete specific search history entry
     */
    @Transactional
    public MessageResponseDto deleteSearch(Long userId, Long searchId) {
        log.info("Deleting search history {} for user {}", searchId, userId);

        searchHistoryRepository.deleteByIdAndUserId(searchId, userId);

        return MessageResponseDto.builder()
            .message("Search history deleted successfully")
            .build();
    }

    /**
     * Clear all search history for a user
     */
    @Transactional
    public MessageResponseDto clearAllHistory(Long userId) {
        log.info("Clearing all search history for user {}", userId);

        long count = searchHistoryRepository.countByUserId(userId);
        searchHistoryRepository.deleteByUserId(userId);

        log.info("Deleted {} search history entries for user {}", count, userId);

        return MessageResponseDto.builder()
            .message(String.format("Cleared %d search history entries", count))
            .build();
    }

    /**
     * Map SearchHistory entity to response DTO
     */
    private SearchHistoryResponseDto mapToResponse(SearchHistory searchHistory) {
        Long stationId = null;
        if (searchHistory.getStation() != null) {
            stationId = searchHistory.getStation().getId();
        }

        return SearchHistoryResponseDto.builder()
            .id(searchHistory.getId())
            .userId(searchHistory.getUser().getId())
            .searchQuery(searchHistory.getSearchQuery())
            .stationId(stationId)
            .stationOcmId(searchHistory.getStationOcmId())
            .stationName(searchHistory.getStationName())
            .stationAddress(searchHistory.getStationAddress())
            .createdAt(searchHistory.getCreatedAt())
            .updatedAt(searchHistory.getUpdatedAt())
            .build();
    }
}
