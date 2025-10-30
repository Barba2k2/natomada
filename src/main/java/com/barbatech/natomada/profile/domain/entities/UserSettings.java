package com.barbatech.natomada.profile.domain.entities;

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
 * Domain Entity: UserSettings
 *
 * Represents user preferences and settings
 */
@Entity
@Table(name = "user_settings")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // General Settings
    @Builder.Default
    @Column(nullable = false, length = 10)
    private String language = "pt_BR";

    @Builder.Default
    @Column(name = "distance_unit", nullable = false, length = 10)
    private String distanceUnit = "km";

    @Builder.Default
    @Column(name = "energy_unit", nullable = false, length = 10)
    private String energyUnit = "kwh";

    @Builder.Default
    @Column(name = "temperature_unit", nullable = false, length = 10)
    private String temperatureUnit = "celsius";

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String theme = "light";

    @Builder.Default
    @Column(name = "map_type", nullable = false, length = 20)
    private String mapType = "standard";

    // Notification Settings
    @Builder.Default
    @Column(name = "push_enabled", nullable = false)
    private Boolean pushEnabled = true;

    @Builder.Default
    @Column(name = "email_enabled", nullable = false)
    private Boolean emailEnabled = true;

    @Builder.Default
    @Column(name = "charging_complete", nullable = false)
    private Boolean chargingComplete = true;

    @Builder.Default
    @Column(name = "charging_status_updates", nullable = false)
    private Boolean chargingStatusUpdates = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean promotional = false;

    // Privacy Settings
    @Builder.Default
    @Column(name = "share_location", nullable = false)
    private Boolean shareLocation = true;

    @Builder.Default
    @Column(name = "profile_public", nullable = false)
    private Boolean profilePublic = true;

    @Builder.Default
    @Column(name = "show_charging_history", nullable = false)
    private Boolean showChargingHistory = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
