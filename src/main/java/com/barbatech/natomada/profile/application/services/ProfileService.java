package com.barbatech.natomada.profile.application.services;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.RefreshToken;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.RefreshTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.infrastructure.storage.S3StorageService;
import com.barbatech.natomada.profile.application.dtos.*;
import com.barbatech.natomada.profile.domain.entities.UserSettings;
import com.barbatech.natomada.profile.infrastructure.repositories.UserSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Service for user profile operations
 */
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final UserSettingsRepository userSettingsRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3StorageService s3StorageService;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/webp"
    );
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    /**
     * Get user profile
     */
    @Transactional(readOnly = true)
    public ProfileResponseDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        String memberSince = user.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return ProfileResponseDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .phone(user.getPhone())
            .avatarUrl(user.getAvatarUrl())
            .bio(user.getBio())
            .emailVerifiedAt(user.getEmailVerifiedAt())
            .stats(ProfileResponseDto.ProfileStatsDto.builder()
                .totalCharges(user.getTotalCharges())
                .totalKwhCharged(user.getTotalKwhCharged().doubleValue())
                .totalStationsVisited(user.getTotalStationsVisited())
                .memberSince(memberSince)
                .build())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }

    /**
     * Update user profile
     */
    @Transactional
    public ProfileResponseDto updateProfile(Long userId, UpdateProfileRequestDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        if (dto.getName() != null) {
            user.setName(dto.getName());
        }
        if (dto.getPhone() != null) {
            user.setPhone(dto.getPhone());
        }
        if (dto.getBio() != null) {
            user.setBio(dto.getBio());
        }

        User updatedUser = userRepository.save(user);

        String memberSince = updatedUser.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE);

        return ProfileResponseDto.builder()
            .id(updatedUser.getId())
            .name(updatedUser.getName())
            .email(updatedUser.getEmail())
            .phone(updatedUser.getPhone())
            .avatarUrl(updatedUser.getAvatarUrl())
            .bio(updatedUser.getBio())
            .emailVerifiedAt(updatedUser.getEmailVerifiedAt())
            .stats(ProfileResponseDto.ProfileStatsDto.builder()
                .totalCharges(updatedUser.getTotalCharges())
                .totalKwhCharged(updatedUser.getTotalKwhCharged().doubleValue())
                .totalStationsVisited(updatedUser.getTotalStationsVisited())
                .memberSince(memberSince)
                .build())
            .createdAt(updatedUser.getCreatedAt())
            .updatedAt(updatedUser.getUpdatedAt())
            .build();
    }

    /**
     * Change user password
     */
    @Transactional
    public MessageResponseDto changePassword(Long userId, ChangePasswordRequestDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Validate current password
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        // Validate new password confirmation
        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirmation())) {
            throw new IllegalArgumentException("Nova senha e confirmação não coincidem");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        // Invalidate all refresh tokens
        refreshTokenRepository.deleteAllByUserId(userId);

        return MessageResponseDto.builder()
            .message("Senha alterada com sucesso. Por favor, faça login novamente.")
            .build();
    }

    /**
     * Get user settings
     */
    @Transactional(readOnly = true)
    public SettingsResponseDto getSettings(Long userId) {
        UserSettings settings = userSettingsRepository.findByUserId(userId)
            .orElseGet(() -> createDefaultSettings(userId));

        return mapToSettingsResponse(settings);
    }

    /**
     * Update user settings
     */
    @Transactional
    public MessageResponseDto updateSettings(Long userId, UpdateSettingsRequestDto dto) {
        UserSettings settings = userSettingsRepository.findByUserId(userId)
            .orElseGet(() -> createDefaultSettings(userId));

        // Update general settings
        if (dto.getLanguage() != null) {
            settings.setLanguage(dto.getLanguage());
        }
        if (dto.getDistanceUnit() != null) {
            settings.setDistanceUnit(dto.getDistanceUnit());
        }
        if (dto.getEnergyUnit() != null) {
            settings.setEnergyUnit(dto.getEnergyUnit());
        }
        if (dto.getTemperatureUnit() != null) {
            settings.setTemperatureUnit(dto.getTemperatureUnit());
        }
        if (dto.getTheme() != null) {
            settings.setTheme(dto.getTheme());
        }
        if (dto.getMapType() != null) {
            settings.setMapType(dto.getMapType());
        }

        // Update notification settings
        if (dto.getNotifications() != null) {
            var notif = dto.getNotifications();
            if (notif.getPushEnabled() != null) {
                settings.setPushEnabled(notif.getPushEnabled());
            }
            if (notif.getEmailEnabled() != null) {
                settings.setEmailEnabled(notif.getEmailEnabled());
            }
            if (notif.getChargingComplete() != null) {
                settings.setChargingComplete(notif.getChargingComplete());
            }
            if (notif.getChargingStatusUpdates() != null) {
                settings.setChargingStatusUpdates(notif.getChargingStatusUpdates());
            }
            if (notif.getPromotional() != null) {
                settings.setPromotional(notif.getPromotional());
            }
        }

        // Update privacy settings
        if (dto.getPrivacy() != null) {
            var privacy = dto.getPrivacy();
            if (privacy.getShareLocation() != null) {
                settings.setShareLocation(privacy.getShareLocation());
            }
            if (privacy.getProfilePublic() != null) {
                settings.setProfilePublic(privacy.getProfilePublic());
            }
            if (privacy.getShowChargingHistory() != null) {
                settings.setShowChargingHistory(privacy.getShowChargingHistory());
            }
        }

        userSettingsRepository.save(settings);

        return MessageResponseDto.builder()
            .message("Configurações atualizadas com sucesso")
            .build();
    }

    /**
     * Upload user avatar
     */
    @Transactional
    public ProfileResponseDto uploadAvatar(Long userId, MultipartFile file) {
        // Validate file
        validateImageFile(file);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Delete old avatar if exists
        if (user.getAvatarUrl() != null && user.getAvatarUrl().contains("s3.")) {
            try {
                String oldKey = extractS3KeyFromUrl(user.getAvatarUrl());
                s3StorageService.deleteFile(oldKey);
            } catch (Exception e) {
                // Log but don't fail if old avatar deletion fails
            }
        }

        // Upload new avatar
        String s3Key = s3StorageService.uploadFile(file, "avatars");
        String avatarUrl = s3StorageService.getPublicUrl(s3Key);

        // Update user
        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);

        return getProfile(userId);
    }

    /**
     * Delete user avatar
     */
    @Transactional
    public ProfileResponseDto deleteAvatar(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Delete from S3 if exists
        if (user.getAvatarUrl() != null && user.getAvatarUrl().contains("s3.")) {
            try {
                String s3Key = extractS3KeyFromUrl(user.getAvatarUrl());
                s3StorageService.deleteFile(s3Key);
            } catch (Exception e) {
                // Log but don't fail
            }
        }

        // Remove avatar URL
        user.setAvatarUrl(null);
        userRepository.save(user);

        return getProfile(userId);
    }

    /**
     * Validate image file
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode estar vazio");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Arquivo muito grande. Tamanho máximo: 5MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Tipo de arquivo não permitido. Use: JPEG, PNG ou WebP");
        }
    }

    /**
     * Extract S3 key from URL
     */
    private String extractS3KeyFromUrl(String url) {
        // Format: https://bucket-name.s3.region.amazonaws.com/key
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex > 0 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        }
        throw new IllegalArgumentException("URL inválida");
    }

    /**
     * Create default settings for a user
     */
    private UserSettings createDefaultSettings(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        return UserSettings.builder()
            .user(user)
            .build();
    }

    /**
     * Map UserSettings entity to SettingsResponseDto
     */
    private SettingsResponseDto mapToSettingsResponse(UserSettings settings) {
        return SettingsResponseDto.builder()
            .userId(settings.getUser().getId())
            .language(settings.getLanguage())
            .distanceUnit(settings.getDistanceUnit())
            .energyUnit(settings.getEnergyUnit())
            .temperatureUnit(settings.getTemperatureUnit())
            .theme(settings.getTheme())
            .mapType(settings.getMapType())
            .notifications(SettingsResponseDto.NotificationSettingsDto.builder()
                .pushEnabled(settings.getPushEnabled())
                .emailEnabled(settings.getEmailEnabled())
                .chargingComplete(settings.getChargingComplete())
                .chargingStatusUpdates(settings.getChargingStatusUpdates())
                .promotional(settings.getPromotional())
                .build())
            .privacy(SettingsResponseDto.PrivacySettingsDto.builder()
                .shareLocation(settings.getShareLocation())
                .profilePublic(settings.getProfilePublic())
                .showChargingHistory(settings.getShowChargingHistory())
                .build())
            .build();
    }
}
