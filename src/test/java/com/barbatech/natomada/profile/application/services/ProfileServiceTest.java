package com.barbatech.natomada.profile.application.services;

import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.RefreshTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.profile.application.dtos.ChangePasswordRequestDto;
import com.barbatech.natomada.profile.application.dtos.ProfileResponseDto;
import com.barbatech.natomada.profile.application.dtos.UpdateProfileRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProfileService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ProfileService Tests")
class ProfileServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfileService profileService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
            .id(1L)
            .name("Test User")
            .email("test@example.com")
            .phone("11999999999")
            .password("hashedPassword")
            .avatarUrl("https://example.com/avatar.jpg")
            .bio("Test bio")
            .totalCharges(10)
            .totalKwhCharged(BigDecimal.valueOf(150.50))
            .totalStationsVisited(5)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }

    @Test
    @DisplayName("Should get user profile successfully")
    void shouldGetUserProfileSuccessfully() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        // Act
        ProfileResponseDto result = profileService.getProfile(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Test User");
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getStats()).isNotNull();
        assertThat(result.getStats().getTotalCharges()).isEqualTo(10);
        assertThat(result.getStats().getTotalKwhCharged()).isEqualTo(150.50);
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> profileService.getProfile(1L))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessageContaining("Usuário não encontrado");
    }

    @Test
    @DisplayName("Should update profile successfully")
    void shouldUpdateProfileSuccessfully() {
        // Arrange
        UpdateProfileRequestDto dto = UpdateProfileRequestDto.builder()
            .name("Updated Name")
            .phone("11988888888")
            .bio("Updated bio")
            .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        ProfileResponseDto result = profileService.updateProfile(1L, dto);

        // Assert
        assertThat(result).isNotNull();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should change password successfully")
    void shouldChangePasswordSuccessfully() {
        // Arrange
        ChangePasswordRequestDto dto = ChangePasswordRequestDto.builder()
            .currentPassword("OldPassword@123")
            .newPassword("NewPassword@123")
            .newPasswordConfirmation("NewPassword@123")
            .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("newHashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        doNothing().when(refreshTokenRepository).deleteAllByUserId(anyLong());

        // Act
        var result = profileService.changePassword(1L, dto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("Senha alterada com sucesso");
        verify(refreshTokenRepository, times(1)).deleteAllByUserId(1L);
    }

    @Test
    @DisplayName("Should throw exception when current password is incorrect")
    void shouldThrowExceptionWhenCurrentPasswordIsIncorrect() {
        // Arrange
        ChangePasswordRequestDto dto = ChangePasswordRequestDto.builder()
            .currentPassword("WrongPassword@123")
            .newPassword("NewPassword@123")
            .newPasswordConfirmation("NewPassword@123")
            .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> profileService.changePassword(1L, dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Senha atual incorreta");

        verify(userRepository, never()).save(any(User.class));
        verify(refreshTokenRepository, never()).deleteAllByUserId(anyLong());
    }

    @Test
    @DisplayName("Should throw exception when passwords don't match")
    void shouldThrowExceptionWhenPasswordsDontMatch() {
        // Arrange
        ChangePasswordRequestDto dto = ChangePasswordRequestDto.builder()
            .currentPassword("OldPassword@123")
            .newPassword("NewPassword@123")
            .newPasswordConfirmation("DifferentPassword@123")
            .build();

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> profileService.changePassword(1L, dto))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("não coincidem");
    }
}
