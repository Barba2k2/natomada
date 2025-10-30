package com.barbatech.natomada.cars.presentation.controllers;

import com.barbatech.natomada.cars.application.dtos.CarResponseDto;
import com.barbatech.natomada.cars.application.dtos.ListCarsRequestDto;
import com.barbatech.natomada.cars.application.services.CarsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller for cars catalog endpoints (public)
 */
@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarsController {

    private final CarsService carsService;

    /**
     * List cars catalog
     * GET /api/cars
     */
    @GetMapping
    public ResponseEntity<CarsListResponseWrapper> listCars(
        @RequestParam(required = false) String brand,
        @RequestParam(required = false) String bodyType,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Integer minPower,
        @RequestParam(required = false) String connector,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "20") Integer limit,
        @RequestParam(required = false, defaultValue = "brand") String sortBy,
        @RequestParam(required = false, defaultValue = "asc") String sortOrder,
        @RequestHeader(value = "Accept-Language", defaultValue = "en_US") String locale
    ) {
        ListCarsRequestDto dto = ListCarsRequestDto.builder()
            .brand(brand)
            .bodyType(bodyType)
            .search(search)
            .minPower(minPower)
            .connector(connector)
            .page(page)
            .limit(limit)
            .sortBy(sortBy)
            .sortOrder(sortOrder)
            .build();

        CarsService.CarsListResponse response = carsService.listCars(dto, locale);

        return ResponseEntity.ok(CarsListResponseWrapper.builder()
            .success(true)
            .data(CarsListResponseWrapper.DataDto.builder()
                .cars(response.getCars())
                .pagination(CarsListResponseWrapper.PaginationDto.builder()
                    .currentPage(response.getCurrentPage())
                    .totalPages(response.getTotalPages())
                    .totalItems(response.getTotalItems())
                    .itemsPerPage(response.getItemsPerPage())
                    .build())
                .build())
            .build());
    }

    /**
     * Get car by ID
     * GET /api/cars/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CarResponseWrapper> getCarById(
        @PathVariable Long id,
        @RequestHeader(value = "Accept-Language", defaultValue = "en_US") String locale
    ) {
        CarResponseDto car = carsService.getCarById(id, locale);

        return ResponseEntity.ok(CarResponseWrapper.builder()
            .success(true)
            .data(car)
            .build());
    }

    /**
     * Get all brands
     * GET /api/cars/brands
     */
    @GetMapping("/brands")
    public ResponseEntity<BrandsResponseWrapper> getBrands() {
        List<String> brands = carsService.getBrands();

        return ResponseEntity.ok(BrandsResponseWrapper.builder()
            .success(true)
            .data(BrandsResponseWrapper.DataDto.builder()
                .brands(brands)
                .build())
            .build());
    }

    /**
     * Get all body types
     * GET /api/cars/body-types
     */
    @GetMapping("/body-types")
    public ResponseEntity<BodyTypesResponseWrapper> getBodyTypes(
        @RequestHeader(value = "Accept-Language", defaultValue = "en_US") String locale
    ) {
        List<String> bodyTypes = carsService.getBodyTypes(locale);

        return ResponseEntity.ok(BodyTypesResponseWrapper.builder()
            .success(true)
            .data(BodyTypesResponseWrapper.DataDto.builder()
                .bodyTypes(bodyTypes)
                .build())
            .build());
    }

    /**
     * Upload car image (admin operation)
     * POST /api/cars/{id}/image
     */
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CarResponseWrapper> uploadCarImage(
        @PathVariable Long id,
        @RequestParam("file") MultipartFile file,
        @RequestHeader(value = "Accept-Language", defaultValue = "en_US") String locale
    ) {
        CarResponseDto car = carsService.uploadCarImage(id, file, locale);

        return ResponseEntity.ok(CarResponseWrapper.builder()
            .success(true)
            .data(car)
            .build());
    }

    // Response wrapper classes
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CarsListResponseWrapper {
        private Boolean success;
        private DataDto data;

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class DataDto {
            private List<CarResponseDto> cars;
            private PaginationDto pagination;
        }

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class PaginationDto {
            private Integer currentPage;
            private Integer totalPages;
            private Long totalItems;
            private Integer itemsPerPage;
        }
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class CarResponseWrapper {
        private Boolean success;
        private CarResponseDto data;
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class BrandsResponseWrapper {
        private Boolean success;
        private DataDto data;

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class DataDto {
            private List<String> brands;
        }
    }

    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class BodyTypesResponseWrapper {
        private Boolean success;
        private DataDto data;

        @lombok.Data
        @lombok.Builder
        @lombok.NoArgsConstructor
        @lombok.AllArgsConstructor
        public static class DataDto {
            private List<String> bodyTypes;
        }
    }
}
