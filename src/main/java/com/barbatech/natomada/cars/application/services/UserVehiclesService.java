package com.barbatech.natomada.cars.application.services;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.auth.application.exceptions.UserNotFoundException;
import com.barbatech.natomada.auth.domain.entities.User;
import com.barbatech.natomada.auth.infrastructure.repositories.UserRepository;
import com.barbatech.natomada.cars.application.dtos.AddUserVehicleRequestDto;
import com.barbatech.natomada.cars.application.dtos.CarResponseDto;
import com.barbatech.natomada.cars.application.dtos.UpdateUserVehicleRequestDto;
import com.barbatech.natomada.cars.application.dtos.UserVehicleResponseDto;
import com.barbatech.natomada.cars.domain.entities.Car;
import com.barbatech.natomada.cars.domain.entities.UserVehicle;
import com.barbatech.natomada.cars.infrastructure.repositories.CarRepository;
import com.barbatech.natomada.cars.infrastructure.repositories.UserVehicleRepository;
import com.barbatech.natomada.infrastructure.events.cars.VehicleAddedEvent;
import com.barbatech.natomada.infrastructure.events.cars.VehicleRemovedEvent;
import com.barbatech.natomada.infrastructure.kafka.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for user vehicle operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserVehiclesService {

    private final UserVehicleRepository userVehicleRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    /**
     * Add vehicle to user account
     */
    @Transactional
    public MessageResponseDto addUserVehicle(Long userId, AddUserVehicleRequestDto dto) {
        log.info("Adding vehicle for user {}: carId={}", userId, dto.getCarId());

        // Check if user exists
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        // Check if car exists
        Car car = carRepository.findById(dto.getCarId())
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado no catálogo"));

        // Check if user already has this car
        if (userVehicleRepository.existsByUserIdAndCarId(userId, dto.getCarId())) {
            throw new IllegalArgumentException("Você já possui este veículo");
        }

        // If it's the first vehicle or isPrimary is true, unset all others
        long vehicleCount = userVehicleRepository.countByUserId(userId);
        boolean shouldBePrimary = vehicleCount == 0 || Boolean.TRUE.equals(dto.getIsPrimary());

        if (shouldBePrimary) {
            userVehicleRepository.unsetAllPrimaryByUserId(userId);
        }

        // Create user vehicle
        UserVehicle userVehicle = UserVehicle.builder()
            .user(user)
            .car(car)
            .nickname(dto.getNickname())
            .licensePlate(dto.getLicensePlate())
            .color(dto.getColor())
            .isPrimary(shouldBePrimary)
            .build();

        userVehicleRepository.save(userVehicle);

        log.info("Vehicle added successfully for user {}", userId);

        // Publish VEHICLE_ADDED event
        VehicleAddedEvent event = VehicleAddedEvent.of(
            userId,
            userVehicle.getId(),
            car.getId(),
            car.getBrand() + " " + car.getModel(),
            dto.getNickname(),
            dto.getColor()
        );
        eventPublisher.publish("natomada.vehicles.events", event);

        return MessageResponseDto.builder()
            .message("Veículo adicionado com sucesso")
            .build();
    }

    /**
     * Get all user vehicles
     */
    @Transactional(readOnly = true)
    public List<UserVehicleResponseDto> getUserVehicles(Long userId, String locale) {
        log.info("Getting vehicles for user {}", userId);

        List<UserVehicle> vehicles = userVehicleRepository.findByUserIdWithCar(userId);

        return vehicles.stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    /**
     * Update user vehicle
     */
    @Transactional
    public MessageResponseDto updateUserVehicle(Long id, Long userId, UpdateUserVehicleRequestDto dto) {
        log.info("Updating vehicle {} for user {}", id, userId);

        UserVehicle vehicle = userVehicleRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));

        // Update fields
        if (dto.getNickname() != null) {
            vehicle.setNickname(dto.getNickname());
        }
        if (dto.getLicensePlate() != null) {
            vehicle.setLicensePlate(dto.getLicensePlate());
        }
        if (dto.getColor() != null) {
            vehicle.setColor(dto.getColor());
        }

        userVehicleRepository.save(vehicle);

        log.info("Vehicle {} updated successfully", id);

        return MessageResponseDto.builder()
            .message("Veículo atualizado com sucesso")
            .build();
    }

    /**
     * Delete user vehicle
     */
    @Transactional
    public MessageResponseDto deleteUserVehicle(Long id, Long userId) {
        log.info("Deleting vehicle {} for user {}", id, userId);

        UserVehicle vehicle = userVehicleRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));

        boolean wasPrimary = vehicle.getIsPrimary();
        Long carId = vehicle.getCar().getId();

        userVehicleRepository.delete(vehicle);

        // If it was primary, set another as primary
        if (wasPrimary) {
            List<UserVehicle> remainingVehicles = userVehicleRepository.findByUserIdWithCar(userId);
            if (!remainingVehicles.isEmpty()) {
                userVehicleRepository.setPrimaryById(remainingVehicles.get(0).getId(), userId);
            }
        }

        log.info("Vehicle {} deleted successfully", id);

        // Publish VEHICLE_REMOVED event
        VehicleRemovedEvent event = VehicleRemovedEvent.of(userId, id, carId);
        eventPublisher.publish("natomada.vehicles.events", event);

        return MessageResponseDto.builder()
            .message("Veículo removido com sucesso")
            .build();
    }

    /**
     * Set vehicle as primary
     */
    @Transactional
    public MessageResponseDto setPrimaryVehicle(Long id, Long userId) {
        log.info("Setting vehicle {} as primary for user {}", id, userId);

        // Check if vehicle exists
        userVehicleRepository.findByIdAndUserId(id, userId)
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));

        // Unset all primary
        userVehicleRepository.unsetAllPrimaryByUserId(userId);

        // Set as primary
        userVehicleRepository.setPrimaryById(id, userId);

        log.info("Vehicle {} set as primary", id);

        return MessageResponseDto.builder()
            .message("Veículo definido como principal")
            .build();
    }

    /**
     * Map UserVehicle entity to response DTO (includes car with imageUrl)
     */
    private UserVehicleResponseDto mapToResponse(UserVehicle vehicle) {
        Car car = vehicle.getCar();

        return UserVehicleResponseDto.builder()
            .id(vehicle.getId())
            .carId(car.getId())
            .nickname(vehicle.getNickname())
            .licensePlate(vehicle.getLicensePlate())
            .color(vehicle.getColor())
            .isPrimary(vehicle.getIsPrimary())
            .totalCharges(vehicle.getTotalCharges())
            .totalKwhCharged(vehicle.getTotalKwhCharged())
            .lastChargedAt(vehicle.getLastChargedAt())
            .car(CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .batteryCapacity(car.getBatteryCapacity())
                .maxSpeed(car.getMaxSpeed())
                .fastChargingPower(car.getFastChargingPower())
                .connector(car.getConnector())
                .bodyType(car.getBodyType())
                .imageUrl(car.getImageUrl()) // URL da imagem
                .createdAt(car.getCreatedAt())
                .updatedAt(car.getUpdatedAt())
                .build())
            .createdAt(vehicle.getCreatedAt())
            .updatedAt(vehicle.getUpdatedAt())
            .build();
    }
}
