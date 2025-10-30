package com.barbatech.natomada.cars.application.services;

import com.barbatech.natomada.cars.application.dtos.CarResponseDto;
import com.barbatech.natomada.cars.application.dtos.ListCarsRequestDto;
import com.barbatech.natomada.cars.domain.entities.Car;
import com.barbatech.natomada.cars.infrastructure.repositories.CarRepository;
import com.barbatech.natomada.infrastructure.storage.S3StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for car catalog operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CarsService {

    private final CarRepository carRepository;
    private final S3StorageService s3StorageService;

    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/webp"
    );
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10MB for car images

    /**
     * List cars with filters and pagination
     */
    @Transactional(readOnly = true)
    public CarsListResponse listCars(ListCarsRequestDto dto, String locale) {
        log.info("Listing cars with filters: brand={}, bodyType={}, search={}",
                 dto.getBrand(), dto.getBodyType(), dto.getSearch());

        // Create sort
        Sort.Direction direction = "desc".equalsIgnoreCase(dto.getSortOrder())
            ? Sort.Direction.DESC
            : Sort.Direction.ASC;

        String sortField = getSortField(dto.getSortBy());
        Sort sort = Sort.by(direction, sortField);

        // Create pageable
        Pageable pageable = PageRequest.of(dto.getPage() - 1, dto.getLimit(), sort);

        // Find cars
        Page<Car> carsPage = carRepository.findWithFilters(
            dto.getBrand(),
            dto.getBodyType(),
            dto.getConnector(),
            dto.getMinPower(),
            dto.getSearch(),
            pageable
        );

        // Map to response
        List<CarResponseDto> cars = carsPage.getContent().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());

        return CarsListResponse.builder()
            .cars(cars)
            .currentPage(dto.getPage())
            .totalPages(carsPage.getTotalPages())
            .totalItems(carsPage.getTotalElements())
            .itemsPerPage(dto.getLimit())
            .build();
    }

    /**
     * Get car by ID
     */
    @Transactional(readOnly = true)
    public CarResponseDto getCarById(Long id, String locale) {
        log.info("Getting car by ID: {}", id);

        Car car = carRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));

        return mapToResponse(car);
    }

    /**
     * Get all brands
     */
    @Transactional(readOnly = true)
    public List<String> getBrands() {
        log.info("Getting all brands");
        return carRepository.findAllBrands();
    }

    /**
     * Get all body types
     */
    @Transactional(readOnly = true)
    public List<String> getBodyTypes(String locale) {
        log.info("Getting all body types for locale: {}", locale);
        return carRepository.findAllBodyTypes();
    }

    /**
     * Upload car image (admin operation)
     */
    @Transactional
    public CarResponseDto uploadCarImage(Long carId, MultipartFile file, String locale) {
        log.info("Uploading image for car ID: {}", carId);

        // Validate file
        validateImageFile(file);

        Car car = carRepository.findById(carId)
            .orElseThrow(() -> new IllegalArgumentException("Carro não encontrado"));

        // Delete old image if exists
        if (car.getImageUrl() != null && car.getImageUrl().contains("s3.")) {
            try {
                String oldKey = extractS3KeyFromUrl(car.getImageUrl());
                s3StorageService.deleteFile(oldKey);
            } catch (Exception e) {
                log.warn("Failed to delete old car image", e);
            }
        }

        // Upload new image
        String s3Key = s3StorageService.uploadFile(file, "cars");
        String imageUrl = s3StorageService.getPublicUrl(s3Key);

        // Update car
        car.setImageUrl(imageUrl);
        carRepository.save(car);

        log.info("Image uploaded successfully for car ID: {}", carId);
        return mapToResponse(car);
    }

    /**
     * Validate image file
     */
    private void validateImageFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode estar vazio");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Arquivo muito grande. Tamanho máximo: 10MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_IMAGE_TYPES.contains(contentType.toLowerCase())) {
            throw new IllegalArgumentException("Tipo de arquivo não permitido. Use: JPEG, PNG ou WebP");
        }
    }

    /**
     * Extract S3 key from URL
     */
    private String extractS3KeyFromUrl(String url) {
        // Format: https://bucket-name.s3.region.amazonaws.com/folder/key
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex > 0 && lastSlashIndex < url.length() - 1) {
            String key = url.substring(lastSlashIndex + 1);
            // Rebuild with folder if needed
            int secondLastSlash = url.lastIndexOf('/', lastSlashIndex - 1);
            if (secondLastSlash > 0 && url.substring(secondLastSlash + 1, lastSlashIndex).equals("cars")) {
                return "cars/" + key;
            }
            return key;
        }
        throw new IllegalArgumentException("URL inválida");
    }

    /**
     * Map Car entity to response DTO (includes imageUrl)
     */
    private CarResponseDto mapToResponse(Car car) {
        return CarResponseDto.builder()
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
            .build();
    }

    /**
     * Get sort field name
     */
    private String getSortField(String sortBy) {
        return switch (sortBy) {
            case "model" -> "model";
            case "power" -> "fastChargingPower";
            default -> "brand";
        };
    }

    /**
     * Response DTO for cars list
     */
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CarsListResponse {
        private List<CarResponseDto> cars;
        private Integer currentPage;
        private Integer totalPages;
        private Long totalItems;
        private Integer itemsPerPage;
    }
}
