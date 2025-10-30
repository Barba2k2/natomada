package com.barbatech.natomada.cars.infrastructure.repositories;

import com.barbatech.natomada.cars.domain.entities.CarTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for CarTranslation entity
 */
@Repository
public interface CarTranslationRepository extends JpaRepository<CarTranslation, Long> {

    /**
     * Find translation by car ID and locale
     */
    Optional<CarTranslation> findByCarIdAndLocale(Long carId, String locale);

    /**
     * Find all translations for a car
     */
    List<CarTranslation> findByCarId(Long carId);

    /**
     * Find all translations by locale
     */
    List<CarTranslation> findByLocale(String locale);
}
