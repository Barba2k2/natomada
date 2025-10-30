package com.barbatech.natomada.cars.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain Entity: CarTranslation
 *
 * Translations for car information (i18n)
 */
@Entity
@Table(name = "car_translations",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_car_locale", columnNames = {"car_id", "locale"})
       },
       indexes = {
           @Index(name = "idx_car_translation_locale", columnList = "locale")
       })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(nullable = false, length = 10)
    private String locale;

    @Column(name = "brand_translated", length = 100)
    private String brandTranslated;

    @Column(name = "model_translated", length = 100)
    private String modelTranslated;

    @Column(name = "body_type_translated", nullable = false, length = 50)
    private String bodyTypeTranslated;
}
