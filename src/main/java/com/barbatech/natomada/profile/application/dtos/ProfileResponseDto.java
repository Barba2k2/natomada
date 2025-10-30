package com.barbatech.natomada.profile.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for user profile response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String avatarUrl;
    private String bio;
    private LocalDateTime emailVerifiedAt;
    private ProfileStatsDto stats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProfileStatsDto {
        private Integer totalCharges;
        private Double totalKwhCharged;
        private Integer totalStationsVisited;
        private String memberSince;
    }
}
