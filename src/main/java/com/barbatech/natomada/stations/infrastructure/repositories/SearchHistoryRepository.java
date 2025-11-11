package com.barbatech.natomada.stations.infrastructure.repositories;

import com.barbatech.natomada.stations.domain.entities.SearchHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for SearchHistory entity
 */
@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    /**
     * Find recent search history for a user
     * Ordered by creation date descending (most recent first)
     */
    @Query("SELECT sh FROM SearchHistory sh " +
           "LEFT JOIN FETCH sh.station " +
           "WHERE sh.user.id = :userId " +
           "ORDER BY sh.createdAt DESC")
    List<SearchHistory> findByUserIdOrderByCreatedAtDesc(
        @Param("userId") Long userId,
        Pageable pageable
    );

    /**
     * Find recent search history for a user (simple version without station fetch)
     */
    List<SearchHistory> findByUserIdOrderByCreatedAtDesc(@Param("userId") Long userId);

    /**
     * Delete specific search history entry
     */
    @Modifying
    @Query("DELETE FROM SearchHistory sh WHERE sh.id = :id AND sh.user.id = :userId")
    void deleteByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * Delete all search history for a user
     */
    @Modifying
    @Query("DELETE FROM SearchHistory sh WHERE sh.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    /**
     * Count search history entries for a user
     */
    long countByUserId(Long userId);
}
