package com.barbatech.natomada.auth.application.services;

import com.barbatech.natomada.auth.application.dtos.LoginRequestDto;
import com.barbatech.natomada.auth.application.dtos.LoginResponseDto;
import com.barbatech.natomada.auth.application.dtos.RegisterRequestDto;
import com.barbatech.natomada.auth.application.exceptions.EmailAlreadyExistsException;
import com.barbatech.natomada.auth.application.exceptions.InvalidCredentialsException;
import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.RefreshToken;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.config.JwtProperties;
import com.barbatech.natomada.auth.infrastructure.repositories.RefreshTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.auth.infrastructure.security.JwtUtil;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AuthService
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private JwtProperties jwtProperties;

    @Mock
    private com.barbatech.natomada.infrastructure.kafka.EventPublisher eventPublisher;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private RegisterRequestDto registerDto;
    private LoginRequestDto loginDto;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
            .id(1L)
            .name("Test User")
            .email("test@example.com")
            .phone("11999999999")
            .password("hashedPassword")
            .totalCharges(0)
            .totalKwhCharged(BigDecimal.ZERO)
            .totalStationsVisited(0)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        registerDto = RegisterRequestDto.builder()
            .name("Test User")
            .email("test@example.com")
            .phone("11999999999")
            .password("Password@123")
            .passwordConfirmation("Password@123")
            .build();

        loginDto = LoginRequestDto.builder()
            .email("test@example.com")
            .password("Password@123")
            .build();
    }

    @Test
    @DisplayName("Should register new user successfully")
    void shouldRegisterNewUserSuccessfully() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        // Act
        var result = authService.register(registerDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("realizado com sucesso");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already exists")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> authService.register(registerDto))
            .isInstanceOf(EmailAlreadyExistsException.class)
            .hasMessageContaining("Email já cadastrado");

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should login successfully with valid credentials")
    void shouldLoginSuccessfullyWithValidCredentials() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(jwtUtil.generateAccessToken(anyLong(), anyString())).thenReturn("accessToken");
        when(jwtUtil.generateRefreshToken()).thenReturn("refreshToken");
        when(jwtProperties.getExpiresIn()).thenReturn(3600000L); // 1 hour in milliseconds

        // Act
        LoginResponseDto result = authService.login(loginDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("accessToken");
        assertThat(result.getRefreshToken()).isEqualTo("refreshToken");
        assertThat(result.getUser()).isNotNull();
        assertThat(result.getUser().getEmail()).isEqualTo("test@example.com");

        verify(refreshTokenRepository, times(1)).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("Should throw exception with invalid password")
    void shouldThrowExceptionWithInvalidPassword() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> authService.login(loginDto))
            .isInstanceOf(InvalidCredentialsException.class)
            .hasMessageContaining("Email ou senha incorretos");

        verify(refreshTokenRepository, never()).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.login(loginDto))
            .isInstanceOf(InvalidCredentialsException.class)
            .hasMessageContaining("Email ou senha incorretos");
    }

    @Test
    @DisplayName("Should logout successfully")
    void shouldLogoutSuccessfully() {
        // Arrange
        Long userId = 1L;
        doNothing().when(refreshTokenRepository).deleteAllByUserId(userId);

        // Act
        var result = authService.logout(userId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getMessage()).contains("Logout realizado com sucesso");
        verify(refreshTokenRepository, times(1)).deleteAllByUserId(userId);
    }

    @Test
    @DisplayName("Should get user details successfully")
    void shouldGetUserDetailsSuccessfully() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(testUser));

        // Act
        var result = authService.getMe(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("test@example.com");
        assertThat(result.getName()).isEqualTo("Test User");
    }

    @Test
    @DisplayName("Should throw exception when getting non-existent user")
    void shouldThrowExceptionWhenGettingNonExistentUser() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authService.getMe(1L))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessageContaining("Usuário não encontrado");
    }
}
