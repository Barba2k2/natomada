package com.barbatech.natomada.infrastructure.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration properties for SMS service (Integraflux)
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sms.integraflux")
public class SmsProperties {

    /**
     * Integraflux API base URL
     */
    private String baseUrl = "https://sms.aresfun.com";

    /**
     * Integration token for Integraflux API
     */
    private String token;

    /**
     * Enable/disable SMS sending (useful for development)
     */
    private boolean enabled = true;
}
