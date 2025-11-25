package com.barbatech.natomada.auth.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT configuration properties with security validation.
 *
 * SECURITY: Validates that JWT secret meets minimum security requirements:
 * - Must be set (no default value)
 * - Must be at least 32 characters (256 bits)
 * - Warns if secret appears to be a common/weak value
 */
@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private static final int MIN_SECRET_LENGTH = 32;
    private static final String[] WEAK_PATTERNS = {
        "secret", "password", "123456", "changeme", "default"
    };

    private String secret;
    private Long expiresIn; // milliseconds

    @PostConstruct
    public void validateSecurity() {
        // Validate secret is set
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                "SECURITY ERROR: JWT_SECRET environment variable must be set. " +
                "Generate a secure secret with: openssl rand -hex 32"
            );
        }

        // Validate minimum length (256 bits = 32 bytes = 64 hex chars, but 32 chars minimum)
        if (secret.length() < MIN_SECRET_LENGTH) {
            throw new IllegalStateException(
                String.format(
                    "SECURITY ERROR: JWT_SECRET must be at least %d characters. Current length: %d. " +
                    "Generate a secure secret with: openssl rand -hex 32",
                    MIN_SECRET_LENGTH, secret.length()
                )
            );
        }

        // Warn about potentially weak secrets
        String lowerSecret = secret.toLowerCase();
        for (String pattern : WEAK_PATTERNS) {
            if (lowerSecret.contains(pattern)) {
                log.warn(
                    "SECURITY WARNING: JWT_SECRET appears to contain a weak pattern '{}'. " +
                    "Consider using a cryptographically secure random value.",
                    pattern
                );
                break;
            }
        }

        log.info("JWT configuration validated successfully (secret length: {} chars)", secret.length());
    }
}
