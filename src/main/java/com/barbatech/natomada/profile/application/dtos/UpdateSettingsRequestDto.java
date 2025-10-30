package com.barbatech.natomada.profile.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating user settings
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSettingsRequestDto {

    private String language;
    private String distanceUnit;
    private String energyUnit;
    private String temperatureUnit;
    private String theme;
    private String mapType;
    private NotificationSettingsDto notifications;
    private PrivacySettingsDto privacy;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotificationSettingsDto {
        private Boolean pushEnabled;
        private Boolean emailEnabled;
        private Boolean chargingComplete;
        private Boolean chargingStatusUpdates;
        private Boolean promotional;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrivacySettingsDto {
        private Boolean shareLocation;
        private Boolean profilePublic;
        private Boolean showChargingHistory;
    }
}
