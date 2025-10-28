package com.barbatech.natomada.auth.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for simple message responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDto {
    private String message;

    public static MessageResponseDto of(String message) {
        return new MessageResponseDto(message);
    }
}
