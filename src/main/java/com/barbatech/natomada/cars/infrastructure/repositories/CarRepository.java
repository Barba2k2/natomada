package com.barbatech.natomada.cars.infrastructure.repositories;

import com.barbatech.natomada.cars.domain.entities.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Car entity
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Find cars with filters (without translations for now - simplified)
     */
    @Query("SELECT c FROM Car c WHERE " +
           "(:brand IS NULL OR c.brand = :brand) AND " +
           "(:bodyType IS NULL OR c.bodyType = :bodyType) AND " +
           "(:connector IS NULL OR c.connector = :connector) AND " +
           "(:minPower IS NULL OR c.fastChargingPower >= :minPower) AND " +
           "(:search IS NULL OR LOWER(c.brand) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(c.model) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Car> findWithFilters(
        @Param("brand") String brand,
        @Param("bodyType") String bodyType,
        @Param("connector") String connector,
        @Param("minPower") Integer minPower,
        @Param("search") String search,
        Pageable pageable
    );

    /**
     * Get all distinct brands
     */
    @Query("SELECT DISTINCT c.brand FROM Car c ORDER BY c.brand")
    List<String> findAllBrands();

    /**
     * Get all distinct body types
     */
    @Query("SELECT DISTINCT c.bodyType FROM Car c ORDER BY c.bodyType")
    List<String> findAllBodyTypes();
}
