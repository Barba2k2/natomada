package com.barbatech.natomada.cars.infrastructure.repositories;

import com.barbatech.natomada.cars.domain.entities.UserVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for UserVehicle entity
 */
@Repository
public interface UserVehicleRepository extends JpaRepository<UserVehicle, Long> {

    /**
     * Find all vehicles for a user with car details
     */
    @Query("SELECT uv FROM UserVehicle uv " +
           "JOIN FETCH uv.car " +
           "WHERE uv.user.id = :userId " +
           "ORDER BY uv.isPrimary DESC, uv.createdAt DESC")
    List<UserVehicle> findByUserIdWithCar(@Param("userId") Long userId);

    /**
     * Find user vehicle by ID and user ID
     */
    @Query("SELECT uv FROM UserVehicle uv " +
           "WHERE uv.id = :id AND uv.user.id = :userId")
    Optional<UserVehicle> findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    /**
     * Find user vehicle by user and car
     */
    @Query("SELECT uv FROM UserVehicle uv " +
           "WHERE uv.user.id = :userId AND uv.car.id = :carId")
    Optional<UserVehicle> findByUserIdAndCarId(@Param("userId") Long userId, @Param("carId") Long carId);

    /**
     * Check if user already has this car
     */
    boolean existsByUserIdAndCarId(Long userId, Long carId);

    /**
     * Count vehicles for a user
     */
    long countByUserId(Long userId);

    /**
     * Unset all primary flags for a user
     */
    @Modifying
    @Query("UPDATE UserVehicle uv SET uv.isPrimary = false WHERE uv.user.id = :userId")
    void unsetAllPrimaryByUserId(@Param("userId") Long userId);

    /**
     * Set vehicle as primary
     */
    @Modifying
    @Query("UPDATE UserVehicle uv SET uv.isPrimary = true WHERE uv.id = :id AND uv.user.id = :userId")
    void setPrimaryById(@Param("id") Long id, @Param("userId") Long userId);
}
