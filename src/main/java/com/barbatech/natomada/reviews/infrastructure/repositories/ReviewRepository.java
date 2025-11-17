package com.barbatech.natomada.reviews.infrastructure.repositories;

import com.barbatech.natomada.reviews.domain.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Review entity
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Find all reviews for a station with user details
     * Ordered by creation date (newest first)
     */
    @Query("SELECT r FROM Review r " +
           "JOIN FETCH r.user " +
           "WHERE r.station.id = :stationId " +
           "ORDER BY r.createdAt DESC")
    List<Review> findByStationIdWithUser(@Param("stationId") Long stationId);

    /**
     * Find all reviews for a station with pagination
     */
    @Query("SELECT r FROM Review r " +
           "JOIN FETCH r.user " +
           "WHERE r.station.id = :stationId " +
           "ORDER BY r.createdAt DESC")
    Page<Review> findByStationIdWithUserPaginated(
        @Param("stationId") Long stationId,
        Pageable pageable
    );

    /**
     * Find all reviews by a user with station details
     */
    @Query("SELECT r FROM Review r " +
           "JOIN FETCH r.station " +
           "WHERE r.user.id = :userId " +
           "ORDER BY r.createdAt DESC")
    List<Review> findByUserIdWithStation(@Param("userId") Long userId);

    /**
     * Find review by user and station
     */
    @Query("SELECT r FROM Review r " +
           "JOIN FETCH r.user " +
           "JOIN FETCH r.station " +
           "WHERE r.user.id = :userId AND r.station.id = :stationId")
    Optional<Review> findByUserIdAndStationId(
        @Param("userId") Long userId,
        @Param("stationId") Long stationId
    );

    /**
     * Check if user has already reviewed a station
     */
    boolean existsByUserIdAndStationId(Long userId, Long stationId);

    /**
     * Count reviews for a station
     */
    long countByStationId(Long stationId);

    /**
     * Calculate average rating for a station
     */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.station.id = :stationId")
    Double calculateAverageRatingByStationId(@Param("stationId") Long stationId);

    /**
     * Get rating distribution for a station
     */
    @Query("SELECT r.rating, COUNT(r) FROM Review r " +
           "WHERE r.station.id = :stationId " +
           "GROUP BY r.rating " +
           "ORDER BY r.rating DESC")
    List<Object[]> getRatingDistributionByStationId(@Param("stationId") Long stationId);
}
