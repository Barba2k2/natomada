package com.barbatech.natomada.cars.presentation.controllers;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.cars.application.dtos.AddUserVehicleRequestDto;
import com.barbatech.natomada.cars.application.dtos.UpdateUserVehicleRequestDto;
import com.barbatech.natomada.cars.application.dtos.UserVehicleResponseDto;
import com.barbatech.natomada.cars.application.services.UserVehiclesService;
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
public class UserVehiclesController {

    private final UserVehiclesService userVehiclesService;

    /**
     * Add vehicle to user account
     * POST /api/user-vehicles
     */
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
    @GetMapping
    public ResponseEntity<UserVehiclesResponseWrapper> getUserVehicles(
        Authentication authentication,
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
     * Update user vehicle
     * PATCH /api/user-vehicles/{id}
     */
    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDto> updateUserVehicle(
        Authentication authentication,
        @PathVariable Long id,
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
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteUserVehicle(
        Authentication authentication,
        @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = userVehiclesService.deleteUserVehicle(id, userId);

        return ResponseEntity.ok(response);
    }

    /**
     * Set vehicle as primary
     * PUT /api/user-vehicles/{id}/primary
     */
    @PutMapping("/{id}/primary")
    public ResponseEntity<MessageResponseDto> setPrimaryVehicle(
        Authentication authentication,
        @PathVariable Long id
    ) {
        Long userId = Long.parseLong(authentication.getName());
        MessageResponseDto response = userVehiclesService.setPrimaryVehicle(id, userId);

        return ResponseEntity.ok(response);
    }

    // Response wrapper class
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
}
