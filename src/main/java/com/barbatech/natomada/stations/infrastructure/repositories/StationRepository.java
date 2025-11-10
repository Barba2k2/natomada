package com.barbatech.natomada.stations.infrastructure.repositories;

import com.barbatech.natomada.stations.domain.entities.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Station entity
 */
@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    /**
     * Find station by OCM ID (format: ocm_123456)
     */
    Optional<Station> findByOcmId(String ocmId);

    /**
     * Find nearby stations using bounding box
     * This is a simplified version - for production, use PostGIS with ST_Distance
     */
    @Query("SELECT s FROM Station s WHERE " +
           "s.latitude BETWEEN :minLat AND :maxLat AND " +
           "s.longitude BETWEEN :minLon AND :maxLon " +
           "ORDER BY s.combinedRating DESC NULLS LAST, s.totalReviews DESC")
    List<Station> findNearbyStations(
        @Param("minLat") BigDecimal minLat,
        @Param("maxLat") BigDecimal maxLat,
        @Param("minLon") BigDecimal minLon,
        @Param("maxLon") BigDecimal maxLon
    );

    /**
     * Check if station exists by OCM ID
     */
    boolean existsByOcmId(String ocmId);
}
