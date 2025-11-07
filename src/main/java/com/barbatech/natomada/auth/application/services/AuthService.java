package com.barbatech.natomada.auth.application.services;

import com.barbatech.natomada.auth.application.dtos.*;
import com.barbatech.natomada.auth.application.exceptions.*;
import com.barbatech.natomada.auth.domain.entities.OtpToken;
import com.barbatech.natomada.auth.domain.entities.PasswordResetToken;
import com.barbatech.natomada.auth.domain.entities.RefreshToken;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.domain.enums.OtpDeliveryMethod;
import com.barbatech.natomada.auth.infrastructure.config.JwtProperties;
import com.barbatech.natomada.auth.infrastructure.repositories.OtpTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.PasswordResetTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.RefreshTokenRepository;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.auth.infrastructure.security.JwtUtil;
import com.barbatech.natomada.infrastructure.email.EmailService;
import com.barbatech.natomada.infrastructure.events.auth.UserLoggedInEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserLoggedOutEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserRegisteredEvent;
import com.barbatech.natomada.infrastructure.kafka.EventPublisher;
import com.barbatech.natomada.infrastructure.sms.IntegrafluxSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

/**
 * Service for authentication operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final OtpTokenRepository otpTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final EventPublisher eventPublisher;
    private final EmailService emailService;
    private final IntegrafluxSmsService smsService;

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

    /**
     * Request password reset - sends email with reset token
     */
    @Transactional
    public MessageResponseDto forgotPassword(ForgotPasswordRequestDto dto) {
        log.info("Password reset requested for email: {}", dto.getEmail());

        // Find user by email
        User user = userRepository.findByEmail(dto.getEmail())
            .orElseThrow(UserNotFoundException::new);

        // Delete any existing password reset tokens for this user
        passwordResetTokenRepository.deleteByUser(user);

        // Generate unique reset token
        String token = UUID.randomUUID().toString();

        // Create password reset token (expires in 1 hour)
        LocalDateTime expiresAt = LocalDateTime.now().plusHours(1);
        PasswordResetToken resetToken = PasswordResetToken.builder()
            .token(token)
            .email(user.getEmail())
            .user(user)
            .expiresAt(expiresAt)
            .build();

        passwordResetTokenRepository.save(resetToken);

        // Send password reset email
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), token, user.getName());
            log.info("Password reset email sent to: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Failed to send password reset email", e);
            throw new RuntimeException("Falha ao enviar email de redefinição de senha");
        }

        return MessageResponseDto.of("Email de redefinição de senha enviado com sucesso");
    }

    /**
     * Validate password reset token
     */
    public MessageResponseDto validateResetToken(String token) {
        log.info("Validating password reset token");

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
            .orElseThrow(() -> new InvalidTokenException("Token inválido"));

        if (!resetToken.isValid()) {
            if (resetToken.isUsed()) {
                throw new InvalidTokenException("Token já foi utilizado");
            }
            if (resetToken.isExpired()) {
                throw new InvalidTokenException("Token expirado");
            }
        }

        return MessageResponseDto.of("Token válido");
    }

    /**
     * Reset password using token
     */
    @Transactional
    public MessageResponseDto resetPassword(ResetPasswordRequestDto dto) {
        log.info("Resetting password with token");

        // Validate password confirmation
        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirmation())) {
            throw new PasswordMismatchException();
        }

        // Find and validate token
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(dto.getToken())
            .orElseThrow(() -> new InvalidTokenException("Token inválido"));

        if (!resetToken.isValid()) {
            if (resetToken.isUsed()) {
                throw new InvalidTokenException("Token já foi utilizado");
            }
            if (resetToken.isExpired()) {
                throw new InvalidTokenException("Token expirado");
            }
        }

        // Get user and update password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);

        // Mark token as used
        resetToken.markAsUsed();
        passwordResetTokenRepository.save(resetToken);

        // Invalidate all refresh tokens for security
        refreshTokenRepository.deleteAllByUserId(user.getId());

        log.info("Password reset successfully for user: {}", user.getId());

        return MessageResponseDto.of("Senha redefinida com sucesso");
    }

    /**
     * Send OTP via email or SMS
     * Generates a 6-digit code and stores it in database
     */
    @Transactional
    public MessageResponseDto sendOtp(SendOtpRequestDto dto) {
        String recipient = dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL
            ? dto.getEmail()
            : dto.getPhoneNumber();

        log.info("Sending OTP via {} to: {}", dto.getDeliveryMethod(), recipient);

        // Validate recipient based on delivery method
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL && (dto.getEmail() == null || dto.getEmail().isBlank())) {
            throw new IllegalArgumentException("Email é obrigatório para envio via EMAIL");
        }
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.SMS && (dto.getPhoneNumber() == null || dto.getPhoneNumber().isBlank())) {
            throw new IllegalArgumentException("Número de telefone é obrigatório para envio via SMS");
        }

        // Delete any existing OTPs for this recipient
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL) {
            otpTokenRepository.deleteByEmail(dto.getEmail());
        } else {
            otpTokenRepository.deleteByPhoneNumber(dto.getPhoneNumber());
        }

        // Generate 6-digit OTP code
        String otpCode = generateOtpCode();

        // Create OTP token (expires in 5 minutes)
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);
        OtpToken otpToken = OtpToken.builder()
            .deliveryMethod(dto.getDeliveryMethod())
            .phoneNumber(dto.getPhoneNumber())
            .email(dto.getEmail())
            .code(otpCode)
            .expiresAt(expiresAt)
            .build();

        otpTokenRepository.save(otpToken);

        // Send OTP based on delivery method
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL) {
            sendOtpViaEmail(dto.getEmail(), otpCode);
        } else {
            sendOtpViaSms(dto.getPhoneNumber(), otpCode);
        }

        return MessageResponseDto.of("Código OTP enviado com sucesso via " +
            (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL ? "email" : "SMS"));
    }

    /**
     * Send OTP via email
     */
    private void sendOtpViaEmail(String email, String otpCode) {
        try {
            String subject = "Seu código de verificação - NaTomada";
            String body = String.format(
                "Olá!\n\n" +
                "Seu código de verificação é: %s\n\n" +
                "Este código expira em 5 minutos.\n\n" +
                "Se você não solicitou este código, ignore este email.\n\n" +
                "Atenciosamente,\n" +
                "Equipe NaTomada",
                otpCode
            );

            emailService.sendEmail(email, subject, body);
            log.info("OTP sent via email to: {}", email);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", email, e);
            // For development, log the code
            log.warn("OTP Code for {}: {} (expires in 5 minutes) - Email sending failed", email, otpCode);
        }
    }

    /**
     * Send OTP via SMS using Integraflux API
     */
    private void sendOtpViaSms(String phoneNumber, String otpCode) {
        try {
            smsService.sendOtpSms(phoneNumber, otpCode);
            log.info("OTP sent via SMS to: {}", phoneNumber);
        } catch (Exception e) {
            log.error("Failed to send OTP SMS to: {}", phoneNumber, e);
            // For development/testing, also log the code if SMS fails
            log.warn("OTP Code for {}: {} (expires in 5 minutes) - SMS sending failed", phoneNumber, otpCode);
            // Don't throw exception - allow process to continue
            // In production, you might want to throw here depending on requirements
        }
    }

    /**
     * Verify OTP and authenticate user
     * Supports both email and SMS verification
     */
    @Transactional
    public LoginResponseDto verifyOtp(VerifyOtpRequestDto dto) {
        String recipient = dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL
            ? dto.getEmail()
            : dto.getPhoneNumber();

        log.info("Verifying OTP via {} for: {}", dto.getDeliveryMethod(), recipient);

        // Validate recipient based on delivery method
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL && (dto.getEmail() == null || dto.getEmail().isBlank())) {
            throw new IllegalArgumentException("Email é obrigatório para verificação via EMAIL");
        }
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.SMS && (dto.getPhoneNumber() == null || dto.getPhoneNumber().isBlank())) {
            throw new IllegalArgumentException("Número de telefone é obrigatório para verificação via SMS");
        }

        // Find the latest valid OTP
        OtpToken otpToken;
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL) {
            otpToken = otpTokenRepository.findLatestValidOtpByEmail(
                dto.getEmail(),
                LocalDateTime.now()
            ).orElseThrow(() -> new InvalidTokenException("Código OTP inválido ou expirado"));
        } else {
            otpToken = otpTokenRepository.findLatestValidOtpByPhoneNumber(
                dto.getPhoneNumber(),
                LocalDateTime.now()
            ).orElseThrow(() -> new InvalidTokenException("Código OTP inválido ou expirado"));
        }

        // Verify the OTP code
        if (!otpToken.getCode().equals(dto.getCode())) {
            throw new InvalidTokenException("Código OTP incorreto");
        }

        // Mark OTP as verified
        otpToken.markAsVerified();
        otpTokenRepository.save(otpToken);

        // Find user by email or phone
        User user;
        if (dto.getDeliveryMethod() == OtpDeliveryMethod.EMAIL) {
            user = userRepository.findByEmail(dto.getEmail()).orElse(null);
            if (user == null) {
                throw new UserNotFoundException("Email não cadastrado. Por favor, complete o cadastro.");
            }
        } else {
            user = userRepository.findByPhone(dto.getPhoneNumber()).orElse(null);
            if (user == null) {
                throw new UserNotFoundException("Número de telefone não cadastrado. Por favor, complete o cadastro.");
            }
        }

        // User exists - generate tokens and log them in
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());
        String refreshTokenStr = jwtUtil.generateRefreshToken();

        // Save refresh token (expires in 7 days)
        LocalDateTime refreshExpiresAt = LocalDateTime.now().plusDays(7);
        RefreshToken refreshToken = RefreshToken.builder()
            .user(user)
            .token(refreshTokenStr)
            .expiresAt(refreshExpiresAt)
            .build();

        refreshTokenRepository.save(refreshToken);

        log.info("User logged in successfully via {} OTP: {}", dto.getDeliveryMethod(), user.getId());

        // Publish USER_LOGGED_IN event
        UserLoggedInEvent loginEvent = UserLoggedInEvent.of(
            user.getId(),
            user.getEmail(),
            "unknown",
            "unknown"
        );
        eventPublisher.publish("natomada.auth.events", loginEvent);

        return LoginResponseDto.builder()
            .accessToken(accessToken)
            .refreshToken(refreshTokenStr)
            .tokenType("Bearer")
            .expiresIn(jwtProperties.getExpiresIn() / 1000)
            .user(UserResponseDto.fromEntity(user))
            .build();
    }

    /**
     * Generate a random 6-digit OTP code
     */
    private String generateOtpCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Generates 100000-999999
        return String.valueOf(code);
    }

    /**
     * Clean up expired password reset tokens (can be scheduled)
     */
    @Transactional
    public void cleanupExpiredResetTokens() {
        log.info("Cleaning up expired password reset tokens");
        passwordResetTokenRepository.deleteExpiredTokens(LocalDateTime.now());
    }

    /**
     * Clean up expired OTP tokens (can be scheduled)
     */
    @Transactional
    public void cleanupExpiredOtps() {
        log.info("Cleaning up expired OTP tokens");
        otpTokenRepository.deleteExpiredOtps(LocalDateTime.now());
    }
}
