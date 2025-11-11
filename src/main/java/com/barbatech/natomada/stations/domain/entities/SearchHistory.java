package com.barbatech.natomada.stations.domain.entities;

import com.barbatech.natomada.auth.domain.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Domain Entity: SearchHistory
 *
 * Represents a user's search history for charging stations.
 * Stores the search query and the selected station (if any).
 */
@Entity
@Table(name = "search_history",
       indexes = {
           @Index(name = "idx_search_history_user_id", columnList = "user_id"),
           @Index(name = "idx_search_history_user_created", columnList = "user_id, created_at DESC")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "search_query", nullable = false, length = 500)
    private String searchQuery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id")
    private Station station;

    @Column(name = "station_ocm_id", length = 255)
    private String stationOcmId;

    @Column(name = "station_name", length = 255)
    private String stationName;

    @Column(name = "station_address", length = 500)
    private String stationAddress;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
