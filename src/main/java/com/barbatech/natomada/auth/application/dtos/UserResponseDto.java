package com.barbatech.natomada.auth.application.dtos;

import com.barbatech.natomada.auth.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for user response (without sensitive data)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String phone;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    private String bio;

    @JsonProperty("total_charges")
    private Integer totalCharges;

    @JsonProperty("total_kwh_charged")
    private BigDecimal totalKwhCharged;

    @JsonProperty("total_stations_visited")
    private Integer totalStationsVisited;

    @JsonProperty("email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    /**
     * Converts User entity to UserResponseDto
     */
    public static UserResponseDto fromEntity(User user) {
        return UserResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatarUrl(user.getAvatarUrl())
            .bio(user.getBio())
            .totalCharges(user.getTotalCharges())
            .totalKwhCharged(user.getTotalKwhCharged())
            .totalStationsVisited(user.getTotalStationsVisited())
            .emailVerifiedAt(user.getEmailVerifiedAt())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }
}
