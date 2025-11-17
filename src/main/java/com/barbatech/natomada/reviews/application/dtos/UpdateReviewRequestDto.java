package com.barbatech.natomada.reviews.application.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for updating an existing review
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequestDto {

    @NotNull(message = "Avaliação é obrigatória")
    @Min(value = 1, message = "Avaliação mínima é 1")
    @Max(value = 5, message = "Avaliação máxima é 5")
    private Integer rating;

    @Size(max = 1000, message = "Comentário não pode ter mais de 1000 caracteres")
    private String comment;
}
