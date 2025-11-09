package com.barbatech.natomada.infrastructure.sms;

import com.barbatech.natomada.infrastructure.i18n.MessageSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * SMS service using Integraflux API
 * Documentation: https://integraflux.apidocumentation.com/reference#tag/default/POST/v1/integration/{token}/send-sms
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IntegrafluxSmsService {

    private final SmsProperties smsProperties;
    private final RestTemplate restTemplate;
    private final MessageSourceService messageService;

    /**
     * Send SMS using Integraflux API
     *
     * @param phoneNumber Phone number in international format (e.g., +5511999999999)
     * @param message     Message content (max 160 characters for single SMS)
     * @throws RuntimeException if SMS sending fails
     */
    public void sendSms(String phoneNumber, String message) {
        if (!smsProperties.isEnabled()) {
            log.warn("SMS sending is disabled. Message for {}: {}", phoneNumber, message);
            return;
        }

        if (smsProperties.getToken() == null || smsProperties.getToken().isBlank()) {
            log.error("Integraflux API token is not configured");
            throw new RuntimeException(messageService.getMessage("sms.not.configured"));
        }

        try {
            String url = String.format(
                "%s/v1/integration/%s/send-sms",
                smsProperties.getBaseUrl(),
                smsProperties.getToken()
            );

            // Clean phone number (remove spaces, dashes, parentheses)
            String cleanPhone = phoneNumber.replaceAll("[\\s\\-()]+", "");

            // Prepare request body according to Integraflux API
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("to", new String[]{cleanPhone});
            requestBody.put("message", message);
            requestBody.put("from", "NaTomada");

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Send request
            log.info("Sending SMS to {} via Integraflux", cleanPhone);
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("SMS sent successfully to {}", cleanPhone);
            } else {
                log.error("Failed to send SMS. Status: {}, Response: {}",
                    response.getStatusCode(), response.getBody());
                throw new RuntimeException(messageService.getMessage("sms.send.failed"));
            }

        } catch (Exception e) {
            log.error("Error sending SMS to {}: {}", phoneNumber, e.getMessage(), e);
            throw new RuntimeException(messageService.getMessage("sms.send.failed"), e);
        }
    }

    /**
     * Send OTP code via SMS
     *
     * @param phoneNumber Phone number
     * @param otpCode     6-digit OTP code
     */
    public void sendOtpSms(String phoneNumber, String otpCode) {
        String message = String.format(
            "Seu código de verificação NaTomada é: %s\n\nEste código expira em 5 minutos.",
            otpCode
        );
        sendSms(phoneNumber, message);
    }
}
