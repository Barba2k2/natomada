package com.barbatech.natomada.auth.application.services;

import com.barbatech.natomada.auth.application.dtos.*;
import com.barbatech.natomada.auth.application.exceptions.*;
import com.barbatech.natomada.auth.domain.entities.RefreshToken;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.config.JwtProperties;
import com.barbatech.natomada.auth.infrastructure.repositories.RefreshTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.auth.infrastructure.security.JwtUtil;
import com.barbatech.natomada.infrastructure.events.auth.UserLoggedInEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserLoggedOutEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserRegisteredEvent;
import com.barbatech.natomada.infrastructure.kafka.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service for authentication operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final EventPublisher eventPublisher;

    /**
     * Register a new user
     */
    @Transactional
    public MessageResponseDto register(RegisterRequestDto dto) {
        log.info("Registering new user with email: {}", dto.getEmail());

        // Validate password confirmation
        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new PasswordMismatchException();
        }

        // Check if email already exists
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        // Check if phone already exists
        if (userRepository.existsByPhone(dto.getPhone())) {
            throw new PhoneAlreadyExistsException();
        }

        // Create new user
        User user = User.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .phone(dto.getPhone())
            .password(passwordEncoder.encode(dto.getPassword()))
            .build();

        userRepository.save(user);

        log.info("User registered successfully with ID: {}", user.getId());

        // Publish USER_REGISTERED event
        UserRegisteredEvent event = UserRegisteredEvent.of(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone()
        );
        eventPublisher.publish("natomada.auth.events", event);

        return MessageResponseDto.of("Cadastro realizado com sucesso! Faça login para continuar.");
    }

    /**
     * Authenticate user and generate tokens
     */
    @Transactional
    public LoginResponseDto login(LoginRequestDto dto) {
        log.info("User attempting to login with email: {}", dto.getEmail());

        // Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(InvalidCredentialsException::new);

        // Verify password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        // Generate access token
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());

        // Generate refresh token
        String refreshTokenStr = jwtUtil.generateRefreshToken();

        // Save refresh token (expires in 7 days)
        LocalDateTime expiresAt = LocalDateTime.now().plusDays(7);
        RefreshToken refreshToken = RefreshToken.builder()
            .user(user)
            .token(refreshTokenStr)
            .expiresAt(expiresAt)
            .build();

        refreshTokenRepository.save(refreshToken);

        log.info("User logged in successfully: {}", user.getId());

        // Publish USER_LOGGED_IN event
        UserLoggedInEvent loginEvent = UserLoggedInEvent.of(
            user.getId(),
            user.getEmail(),
            "unknown", // TODO: Get from HttpServletRequest
            "unknown"  // TODO: Get from HttpServletRequest
        );
        eventPublisher.publish("natomada.auth.events", loginEvent);

        return LoginResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshTokenStr)
            .tokenType("Bearer")
            .expiresIn(jwtProperties.getExpiresIn() / 1000) // Convert to seconds
            .user(UserResponseDto.fromEntity(user))
            .build();
    }

    /**
     * Logout user by invalidating refresh tokens
     */
    @Transactional
    public MessageResponseDto logout(Long userId) {
        log.info("User {} logging out", userId);

        refreshTokenRepository.deleteAllByUserId(userId);

        // Publish USER_LOGGED_OUT event
        UserLoggedOutEvent logoutEvent = UserLoggedOutEvent.of(userId);
        eventPublisher.publish("natomada.auth.events", logoutEvent);

        return MessageResponseDto.of("Logout realizado com sucesso");
    }

    /**
     * Refresh access token using refresh token
     */
    @Transactional
    public LoginResponseDto refreshToken(String refreshTokenStr) {
        log.info("Refreshing access token");

        // Find refresh token
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenStr)
            .orElseThrow(() -> new InvalidTokenException("Refresh token inválido ou expirado"));

        // Check if expired
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidTokenException("Refresh token expirado");
        }

        // Get user
        User user = refreshToken.getUser();

        // Generate new access token
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());

        log.info("Access token refreshed for user: {}", user.getId());

        return LoginResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshTokenStr) // Return same refresh token
            .tokenType("Bearer")
            .expiresIn(jwtProperties.getExpiresIn() / 1000)
            .user(UserResponseDto.fromEntity(user))
            .build();
    }

    /**
     * Get authenticated user details
     */
    public UserResponseDto getMe(Long userId) {
        log.info("Getting user details for ID: {}", userId);

        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        return UserResponseDto.fromEntity(user);
    }
}
