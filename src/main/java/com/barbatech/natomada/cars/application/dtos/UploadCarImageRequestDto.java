package com.barbatech.natomada.cars.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for uploading car image
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadCarImageRequestDto {
    private Long carId;
}
