package com.barbatech.natomada.profile.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.profile.application.dtos.*;
import com.barbatech.natomada.profile.application.services.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Profile & Settings", description = "Endpoints para gerenciamento de perfil e configurações do usuário")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * Get user profile
     * GET /api/profile
     */
    @Operation(summary = "Obter perfil do usuário", description = "Retorna os dados do perfil do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil recuperado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
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
    @Operation(summary = "Atualizar perfil", description = "Atualiza os dados do perfil do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
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
    @Operation(summary = "Alterar senha", description = "Altera a senha do usuário autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Senha atual incorreta ou nova senha inválida"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
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
    @Operation(summary = "Upload de avatar", description = "Faz upload da foto de perfil do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Avatar enviado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Arquivo inválido"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
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
