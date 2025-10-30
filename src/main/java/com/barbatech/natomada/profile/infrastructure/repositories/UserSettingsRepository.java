package com.barbatech.natomada.profile.infrastructure.repositories;

import com.barbatech.natomada.profile.domain.entities.UserSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for UserSettings entity
 */
@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long> {

    /**
     * Find user settings by user ID
     */
    Optional<UserSettings> findByUserId(Long userId);

    /**
     * Check if settings exist for a user
     */
    boolean existsByUserId(Long userId);

    /**
     * Delete settings by user ID
     */
    void deleteByUserId(Long userId);
}
