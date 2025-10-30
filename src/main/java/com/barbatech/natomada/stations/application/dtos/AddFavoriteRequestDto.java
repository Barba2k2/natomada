package com.barbatech.natomada.stations.application.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for adding a station to favorites
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFavoriteRequestDto {

    @Size(max = 500, message = "Notas devem ter no máximo 500 caracteres")
    private String notes;
}
