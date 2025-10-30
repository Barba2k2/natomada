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
 * Domain Entity: Favorite
 *
 * Represents a user's favorite charging station
 */
@Entity
@Table(name = "favorites",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_user_station", columnNames = {"user_id", "station_id"})
       },
       indexes = {
           @Index(name = "idx_favorite_user_id", columnList = "user_id"),
           @Index(name = "idx_favorite_station_id", columnList = "station_id")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private Station station;

    @Column(length = 500)
    private String notes;

    @Column(name = "last_visited_at")
    private LocalDateTime lastVisitedAt;

    @Builder.Default
    @Column(name = "visit_count", nullable = false)
    private Integer visitCount = 0;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
