package com.barbatech.natomada.cars.domain.entities;

import com.barbatech.natomada.auth.domain.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain Entity: UserVehicle
 *
 * Represents a vehicle owned by a user
 */
@Entity
@Table(name = "user_vehicles",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_user_car", columnNames = {"user_id", "car_id"})
       },
       indexes = {
           @Index(name = "idx_user_vehicle_user_id", columnList = "user_id"),
           @Index(name = "idx_user_vehicle_car_id", columnList = "car_id")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(length = 100)
    private String nickname;

    @Column(name = "license_plate", length = 20)
    private String licensePlate;

    @Column(length = 50)
    private String color;

    @Builder.Default
    @Column(name = "is_primary", nullable = false)
    private Boolean isPrimary = false;

    @Builder.Default
    @Column(name = "total_charges", nullable = false)
    private Integer totalCharges = 0;

    @Builder.Default
    @Column(name = "total_kwh_charged", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalKwhCharged = BigDecimal.ZERO;

    @Column(name = "last_charged_at")
    private LocalDateTime lastChargedAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
