package com.barbatech.natomada.contact.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.contact.application.dtos.ContactRequestDto;
import com.barbatech.natomada.contact.application.services.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for contact form endpoints
 */
@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@Tag(name = "Contact", description = "Endpoints para formulário de contato")
@io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
public class ContactController {

    private final ContactService contactService;
    private final UserRepository userRepository;

    /**
     * Send contact message
     * POST /api/contact
     */
    @Operation(summary = "Enviar mensagem de contato", description = "Envia uma mensagem de contato/suporte")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mensagem enviada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    public ResponseEntity<MessageResponseDto> sendContactMessage(
        @Valid @RequestBody ContactRequestDto dto,
        Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        String userEmail = userRepository.findById(userId)
            .map(User::getEmail)
            .orElse(null);

        MessageResponseDto response = contactService.sendContactMessage(dto, userEmail);
        return ResponseEntity.ok(response);
    }
}
