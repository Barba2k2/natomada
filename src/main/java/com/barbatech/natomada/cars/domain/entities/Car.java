package com.barbatech.natomada.cars.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain Entity: Car (Electric Vehicle)
 *
 * Represents an electric vehicle in the catalog
 */
@Entity
@Table(name = "cars", indexes = {
    @Index(name = "idx_car_brand", columnList = "brand"),
    @Index(name = "idx_car_body_type", columnList = "body_type")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(name = "battery_capacity", nullable = false, precision = 10, scale = 2)
    private BigDecimal batteryCapacity;

    @Column(name = "max_speed", nullable = false)
    private Integer maxSpeed;

    @Column(name = "fast_charging_power", nullable = false)
    private Integer fastChargingPower;

    @Column(nullable = false, length = 50)
    private String connector;

    @Column(name = "body_type", nullable = false, length = 50)
    private String bodyType;

    // URL da imagem do carro
    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Relationships
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CarTranslation> translations = new ArrayList<>();
}
