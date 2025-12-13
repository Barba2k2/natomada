package com.barbatech.natomada.reviews.application.services;

import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.reviews.application.dtos.CreateReviewRequestDto;
import com.barbatech.natomada.reviews.application.dtos.ReviewResponseDto;
import com.barbatech.natomada.reviews.application.dtos.UpdateReviewRequestDto;
import com.barbatech.natomada.reviews.application.exceptions.ReviewExpiredException;
import com.barbatech.natomada.reviews.application.exceptions.ReviewNotFoundException;
import com.barbatech.natomada.reviews.domain.entities.Review;
import com.barbatech.natomada.reviews.infrastructure.repositories.ReviewRepository;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for review operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final StationRepository stationRepository;
    private final UserRepository userRepository;

    /**
     * Submit a new review
     */
    @Transactional
    public ReviewResponseDto submitReview(Long userId, Long stationId, CreateReviewRequestDto request) {
        log.info("User {} submitting review for station {}", userId, stationId);

        // Check if user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Check if station exists
        Station station = stationRepository.findById(stationId)
            .orElseThrow(() -> new IllegalArgumentException("Estação não encontrada"));

        // Check if user has already reviewed this station
        if (reviewRepository.existsByUserIdAndStationId(userId, stationId)) {
            throw new IllegalStateException("Você já avaliou esta estação");
        }

        // Create review
        Review review = Review.builder()
            .user(user)
            .station(station)
            .rating(request.getRating())
            .comment(request.getComment())
            .build();

        Review savedReview = reviewRepository.save(review);

        log.info("Review {} created for station {}", savedReview.getId(), stationId);

        return mapToResponse(savedReview);
    }

    /**
     * Get all reviews for a station
     */
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getStationReviews(Long stationId, Integer limit, Integer offset) {
        log.info("Getting reviews for station: {} (limit: {}, offset: {})", stationId, limit, offset);

        // Check if station exists
        if (!stationRepository.existsById(stationId)) {
            throw new IllegalArgumentException("Estação não encontrada");
        }

        List<Review> reviews;

        if (limit != null && offset != null) {
            Pageable pageable = PageRequest.of(offset / limit, limit);
            Page<Review> page = reviewRepository.findByStationIdWithUserPaginated(stationId, pageable);
            reviews = page.getContent();
        } else {
            reviews = reviewRepository.findByStationIdWithUser(stationId);
        }

        return reviews.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Get all reviews by a user
     */
    @Transactional(readOnly = true)
    public List<ReviewResponseDto> getUserReviews(Long userId) {
        log.info("Getting reviews for user: {}", userId);

        // Check if user exists
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        List<Review> reviews = reviewRepository.findByUserIdWithStation(userId);

        return reviews.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Update a review
     */
    @Transactional
    public ReviewResponseDto updateReview(Long userId, Long reviewId, UpdateReviewRequestDto request) {
        log.info("User {} updating review {}", userId, reviewId);

        // Find review
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException("Avaliação não encontrada"));

        // Check if user owns the review
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Você não tem permissão para editar esta avaliação");
        }

        // Validate review is within 7-day edit window
        validateReviewEditWindow(review);

        // Update fields
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updatedReview = reviewRepository.save(review);

        log.info("Review {} updated", reviewId);

        return mapToResponse(updatedReview);
    }

    /**
     * Delete a review
     */
    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        log.info("User {} deleting review {}", userId, reviewId);

        // Find review
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new ReviewNotFoundException("Avaliação não encontrada"));

        // Check if user owns the review
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalStateException("Você não tem permissão para deletar esta avaliação");
        }

        // Validate review is within 7-day edit window
        validateReviewEditWindow(review);

        reviewRepository.delete(review);

        log.info("Review {} deleted", reviewId);
    }

    /**
     * Validate that review is within 7-day edit window
     */
    private void validateReviewEditWindow(Review review) {
        LocalDateTime now = LocalDateTime.now();
        long daysSinceCreation = ChronoUnit.DAYS.between(review.getCreatedAt(), now);

        if (daysSinceCreation >= 7) {
            throw new ReviewExpiredException("Não é possível editar ou deletar avaliações com mais de 7 dias");
        }
    }

    /**
     * Map Review entity to ReviewResponseDto
     */
    private ReviewResponseDto mapToResponse(Review review) {
        return ReviewResponseDto.builder()
            .id(review.getId())
            .stationId(review.getStation().getId())
            .stationName(review.getStation().getName())
            .userId(review.getUser().getId())
            .userName(review.getUser().getName())
            .userProfileImage(review.getUser().getAvatarUrl())
            .rating(review.getRating())
            .comment(review.getComment())
            .createdAt(review.getCreatedAt())
            .updatedAt(review.getUpdatedAt())
            .build();
    }
}
