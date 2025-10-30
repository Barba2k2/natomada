package com.barbatech.natomada.profile.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.profile.application.dtos.*;
import com.barbatech.natomada.profile.application.services.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller for profile and settings endpoints
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Get user profile
     * GET /api/profile
     */
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        ProfileResponseDto profile = profileService.getProfile(userId);

        return ResponseEntity.ok(ProfileResponse.builder()
            .success(true)
            .data(profile)
            .build());
    }

    /**
     * Update user profile
     * PUT /api/profile
     */
    @PutMapping("/profile")
    public ResponseEntity<ProfileUpdateResponse> updateProfile(
        Authentication authentication,
        @Valid @RequestBody UpdateProfileRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        ProfileResponseDto profile = profileService.updateProfile(userId, dto);

        return ResponseEntity.ok(ProfileUpdateResponse.builder()
            .success(true)
            .message("Perfil atualizado com sucesso")
            .data(profile)
            .build());
    }

    /**
     * Change user password
     * PUT /api/profile/password
     */
    @PutMapping("/profile/password")
    public ResponseEntity<MessageResponseDto> changePassword(
        Authentication authentication,
        @Valid @RequestBody ChangePasswordRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = profileService.changePassword(userId, dto);

        return ResponseEntity.ok(response);
    }

    /**
     * Get user settings
     * GET /api/settings
     */
    @GetMapping("/settings")
    public ResponseEntity<SettingsResponse> getSettings(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        SettingsResponseDto settings = profileService.getSettings(userId);

        return ResponseEntity.ok(SettingsResponse.builder()
            .success(true)
            .data(settings)
            .build());
    }

    /**
     * Update user settings
     * PUT /api/settings
     */
    @PutMapping("/settings")
    public ResponseEntity<MessageResponseDto> updateSettings(
        Authentication authentication,
        @Valid @RequestBody UpdateSettingsRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = profileService.updateSettings(userId, dto);

        return ResponseEntity.ok(response);
    }

    /**
     * Upload user avatar
     * POST /api/profile/avatar
     */
    @PostMapping(value = "/profile/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponse> uploadAvatar(
        Authentication authentication,
        @RequestParam("file") MultipartFile file
    ) {
        Long userId = Long.parseLong(authentication.getName());
        ProfileResponseDto profile = profileService.uploadAvatar(userId, file);

        return ResponseEntity.ok(ProfileResponse.builder()
            .success(true)
            .data(profile)
            .build());
    }

    /**
     * Delete user avatar
     * DELETE /api/profile/avatar
     */
    @DeleteMapping("/profile/avatar")
    public ResponseEntity<ProfileResponse> deleteAvatar(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        ProfileResponseDto profile = profileService.deleteAvatar(userId);

        return ResponseEntity.ok(ProfileResponse.builder()
            .success(true)
            .data(profile)
            .build());
    }

    // Response wrapper classes
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class ProfileResponse {
        private Boolean success;
        private ProfileResponseDto data;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class ProfileUpdateResponse {
        private Boolean success;
        private String message;
        private ProfileResponseDto data;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    private static class SettingsResponse {
        private Boolean success;
        private SettingsResponseDto data;
    }
}
