package com.barbatech.natomada.stations.infrastructure.repositories;

import com.barbatech.natomada.stations.domain.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for Favorite entity
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Find all favorites for a user with station details
     */
    @Query("SELECT f FROM Favorite f " +
           "JOIN FETCH f.station " +
           "WHERE f.user.id = :userId " +
           "ORDER BY f.createdAt DESC")
    List<Favorite> findByUserIdWithStation(@Param("userId") Long userId);

    /**
     * Find favorite by user and station
     */
    @Query("SELECT f FROM Favorite f " +
           "WHERE f.user.id = :userId AND f.station.id = :stationId")
    Optional<Favorite> findByUserIdAndStationId(
        @Param("userId") Long userId,
        @Param("stationId") Long stationId
    );

    /**
     * Check if station is favorited by user
     */
    boolean existsByUserIdAndStationId(Long userId, Long stationId);

    /**
     * Delete favorite by user and station
     */
    @Modifying
    @Query("DELETE FROM Favorite f WHERE f.user.id = :userId AND f.station.id = :stationId")
    void deleteByUserIdAndStationId(@Param("userId") Long userId, @Param("stationId") Long stationId);

    /**
     * Count favorites for a user
     */
    long countByUserId(Long userId);
}
