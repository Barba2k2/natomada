package com.barbatech.natomada.auth.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.*;
import com.barbatech.natomada.auth.application.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints para autenticação e gerenciamento de usuários")
public class AuthController {

    private final AuthService authService;

    /**
     * Register a new user
     * POST /api/auth/register
     */
    @Operation(
        summary = "Registrar novo usuário",
        description = "Cria uma nova conta de usuário no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuário registrado com sucesso",
            content = @Content(schema = @Schema(implementation = MessageResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de registro inválidos"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email já cadastrado"
        )
    })
    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        MessageResponseDto response = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Login
     * POST /api/auth/login
     */
    @Operation(
        summary = "Fazer login",
        description = "Autentica um usuário e retorna tokens de acesso e refresh"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login realizado com sucesso",
            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Credenciais inválidas"
        )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        LoginResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Logout
     * POST /api/auth/logout
     */
    @Operation(
        summary = "Fazer logout",
        description = "Invalida o refresh token do usuário autenticado",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Logout realizado com sucesso",
            content = @Content(schema = @Schema(implementation = MessageResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado"
        )
    })
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
    @Operation(
        summary = "Renovar token de acesso",
        description = "Gera um novo access token usando o refresh token"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token renovado com sucesso",
            content = @Content(schema = @Schema(implementation = LoginResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Refresh token inválido ou expirado"
        )
    })
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto dto) {
        LoginResponseDto response = authService.refreshToken(dto.getRefreshToken());
        return ResponseEntity.ok(response);
    }

    /**
     * Get authenticated user details
     * GET /api/auth/me
     */
    @Operation(
        summary = "Obter dados do usuário autenticado",
        description = "Retorna as informações do usuário atualmente autenticado",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Dados do usuário recuperados com sucesso",
            content = @Content(schema = @Schema(implementation = UserResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado"
        )
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(Authentication authentication) {
        Long userId = Long.parseLong(authentication.getName());
        UserResponseDto response = authService.getMe(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Request password reset
     * POST /api/auth/forgot-password
     */
    @Operation(
        summary = "Solicitar redefinição de senha",
        description = "Envia um email com link para redefinir a senha"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Email de redefinição enviado com sucesso",
            content = @Content(schema = @Schema(implementation = MessageResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado"
        )
    })
    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDto> forgotPassword(@Valid @RequestBody ForgotPasswordRequestDto dto) {
        MessageResponseDto response = authService.forgotPassword(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Validate password reset token
     * POST /api/auth/validate-reset-token
     */
    @Operation(
        summary = "Validar token de redefinição",
        description = "Verifica se o token de redefinição de senha é válido"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Token válido",
            content = @Content(schema = @Schema(implementation = MessageResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Token inválido, expirado ou já utilizado"
        )
    })
    @PostMapping("/validate-reset-token")
    public ResponseEntity<MessageResponseDto> validateResetToken(@Valid @RequestBody ValidateResetTokenRequestDto dto) {
        MessageResponseDto response = authService.validateResetToken(dto.getToken());
        return ResponseEntity.ok(response);
    }

    /**
     * Reset password using token
     * POST /api/auth/reset-password
     */
    @Operation(
        summary = "Redefinir senha",
        description = "Redefine a senha do usuário usando o token recebido por email"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Senha redefinida com sucesso",
            content = @Content(schema = @Schema(implementation = MessageResponseDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Token inválido ou dados inválidos"
        )
    })
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDto> resetPassword(@Valid @RequestBody ResetPasswordRequestDto dto) {
        MessageResponseDto response = authService.resetPassword(dto);
        return ResponseEntity.ok(response);
    }
}