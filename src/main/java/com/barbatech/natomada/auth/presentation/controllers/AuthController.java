package com.barbatech.natomada.auth.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.*;
import com.barbatech.natomada.auth.application.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for authentication endpoints
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        MessageResponseDto response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        LoginResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Logout
     * POST /api/auth/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = authService.logout(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Refresh access token
     * POST /api/auth/refresh-token
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto dto) {
        LoginResponseDto response = authService.refreshToken(dto.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    /**
     * Get authenticated user details
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        UserResponseDto response = authService.getMe(userId);
        return ResponseEntity.ok(response);
    }
}