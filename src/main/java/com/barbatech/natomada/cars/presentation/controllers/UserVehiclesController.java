package com.barbatech.natomada.cars.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.cars.application.dtos.AddUserVehicleRequestDto;
import com.barbatech.natomada.cars.application.dtos.UpdateUserVehicleRequestDto;
import com.barbatech.natomada.cars.application.dtos.UserVehicleResponseDto;
import com.barbatech.natomada.cars.application.services.UserVehiclesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

import java.util.List;

/**
 * Controller for user vehicles endpoints (authenticated)
 */
@RestController
@RequestMapping("/api/user-vehicles")
@RequiredArgsConstructor
@Tag(name = "User Vehicles", description = "Endpoints para gerenciamento de veículos do usuário")
@SecurityRequirement(name = "bearerAuth")
public class UserVehiclesController {

    private final UserVehiclesService userVehiclesService;

    /**
     * Add vehicle to user account
     * POST /api/user-vehicles
     */
    @Operation(summary = "Adicionar veículo", description = "Adiciona um veículo à conta do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Veículo adicionado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping
    public ResponseEntity<MessageResponseDto> addUserVehicle(
        Authentication authentication,
        @Valid @RequestBody AddUserVehicleRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = userVehiclesService.addUserVehicle(userId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get user vehicles
     * GET /api/user-vehicles
     */
    @Operation(summary = "Listar veículos do usuário", description = "Retorna todos os veículos cadastrados pelo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de veículos recuperada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @GetMapping
    public ResponseEntity<UserVehiclesResponseWrapper> getUserVehicles(
        Authentication authentication,
        @Parameter(description = "Idioma para tradução de campos", example = "pt_BR")
        @RequestHeader(value = "Accept-Language", defaultValue = "en_US") String locale
    ) {
        Long userId = Long.parseLong(authentication.getName());
        List<UserVehicleResponseDto> vehicles = userVehiclesService.getUserVehicles(userId, locale);

        return ResponseEntity.ok(UserVehiclesResponseWrapper.builder()
            .success(true)
            .data(UserVehiclesResponseWrapper.DataDto.builder()
                .vehicles(vehicles)
                .build())
            .build());
    }

    /**
     * Get primary vehicle
     * GET /api/user-vehicles/primary
     */
    @Operation(summary = "Obter veículo principal", description = "Retorna o veículo principal do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo principal recuperado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Veículo principal não encontrado")
    })
    @GetMapping("/primary")
    public ResponseEntity<UserVehicleResponseWrapper> getPrimaryVehicle(
        Authentication authentication
    ) {
        Long userId = Long.parseLong(authentication.getName());
        UserVehicleResponseDto vehicle = userVehiclesService.getPrimaryVehicle(userId);

        return ResponseEntity.ok(UserVehicleResponseWrapper.builder()
            .success(true)
            .data(vehicle)
            .build());
    }

    /**
     * Get user vehicle by ID
     * GET /api/user-vehicles/{id}
     */
    @Operation(summary = "Obter veículo por ID", description = "Retorna os detalhes de um veículo específico do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo recuperado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserVehicleResponseWrapper> getUserVehicleById(
        Authentication authentication,
        @Parameter(description = "ID do veículo do usuário", required = true) @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        UserVehicleResponseDto vehicle = userVehiclesService.getUserVehicleById(id, userId);

        return ResponseEntity.ok(UserVehicleResponseWrapper.builder()
            .success(true)
            .data(vehicle)
            .build());
    }

    /**
     * Update user vehicle
     * PATCH /api/user-vehicles/{id}
     */
    @Operation(summary = "Atualizar veículo", description = "Atualiza informações de um veículo do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDto> updateUserVehicle(
        Authentication authentication,
        @Parameter(description = "ID do veículo do usuário", required = true) @PathVariable Long id,
        @Valid @RequestBody UpdateUserVehicleRequestDto dto
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = userVehiclesService.updateUserVehicle(id, userId, dto);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete user vehicle
     * DELETE /api/user-vehicles/{id}
     */
    @Operation(summary = "Remover veículo", description = "Remove um veículo da conta do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo removido com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteUserVehicle(
        Authentication authentication,
        @Parameter(description = "ID do veículo do usuário", required = true) @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = userVehiclesService.deleteUserVehicle(id, userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Set vehicle as primary
     * PUT /api/user-vehicles/{id}/primary
     */
    @Operation(summary = "Definir veículo principal", description = "Define um veículo como principal do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo principal definido com sucesso"),
        @ApiResponse(responseCode = "401", description = "Não autenticado"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @PutMapping("/{id}/primary")
    public ResponseEntity<MessageResponseDto> setPrimaryVehicle(
        Authentication authentication,
        @Parameter(description = "ID do veículo do usuário", required = true) @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = userVehiclesService.setPrimaryVehicle(id, userId);

        return ResponseEntity.ok(response);
    }

    // Response wrapper classes
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class UserVehiclesResponseWrapper {
        private Boolean success;
        private DataDto data;

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class DataDto {
            private List<UserVehicleResponseDto> vehicles;
        }
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class UserVehicleResponseWrapper {
        private Boolean success;
        private UserVehicleResponseDto data;
    }
}
