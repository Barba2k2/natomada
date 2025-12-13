package com.barbatech.natomada.contact.application.services;

import com.barbatech.natomada.auth.application.dtos.MessageResponseDto;
import com.barbatech.natomada.contact.application.dtos.ContactRequestDto;
import com.barbatech.natomada.infrastructure.i18n.MessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for contact form operations
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ContactService {

    private final MessageSourceService messageService;

    /**
     * Process contact form submission
     */
    public MessageResponseDto sendContactMessage(ContactRequestDto dto, String userEmail) {
        log.info("Contact form submission received:");
        log.info("Subject: {}", dto.getSubject());
        log.info("Message: {}", dto.getMessage());
        log.info("From: {}", userEmail != null ? userEmail : dto.getEmail());

        // TODO: Implement email sending logic
        // For now, just log the message
        // Future: Use emailService.sendContactNotification(dto, userEmail);

        return MessageResponseDto.builder()
            .message(messageService.getMessage("contact.message.sent"))
            .build();
    }
}
