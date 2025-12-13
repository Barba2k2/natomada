package com.barbatech.natomada.contact.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for contact form submission
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequestDto {

    @NotBlank(message = "{contact.subject.required}")
    @Size(max = 200, message = "{contact.subject.max}")
    private String subject;

    @NotBlank(message = "{contact.message.required}")
    @Size(max = 5000, message = "{contact.message.max}")
    private String message;

    @Email(message = "{contact.email.invalid}")
    private String email;
}
