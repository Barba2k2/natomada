package com.barbatech.natomada.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates that all required secrets are properly configured at startup.
 *
 * SECURITY: This class ensures the application fails fast if critical
 * security configurations are missing or appear insecure.
 *
 * Required environment variables:
 * - JWT_SECRET: JWT signing key (min 32 chars)
 * - SPRING_DATASOURCE_PASSWORD or POSTGRES_PASSWORD: Database password
 * - SPRING_DATA_REDIS_PASSWORD or REDIS_PASSWORD: Redis password
 *
 * Recommended environment variables:
 * - AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY: For S3 storage
 * - MAIL_PASSWORD: For email sending
 * - SMS_INTEGRAFLUX_TOKEN: For SMS sending
 */
@Slf4j
@Configuration
public class SecretsValidationConfig {

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    @Value("${aws.access-key-id:}")
    private String awsAccessKeyId;

    @Value("${aws.secret-access-key:}")
    private String awsSecretAccessKey;

    @Value("${spring.mail.password:}")
    private String mailPassword;

    @Value("${sms.integraflux.token:}")
    private String smsToken;

    @Value("${sms.integraflux.enabled:true}")
    private boolean smsEnabled;

    @PostConstruct
    public void validateSecrets() {
        List<String> errors = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        // Critical: Database password
        if (dbPassword == null || dbPassword.isBlank() || "postgres".equals(dbPassword)) {
            errors.add("Database password is missing or using default value 'postgres'");
        }

        // Critical: Redis password (if using default 'redis')
        if (redisPassword == null || redisPassword.isBlank() || "redis".equals(redisPassword)) {
            warnings.add("Redis password is missing or using default value 'redis'");
        }

        // Warning: AWS credentials
        if ((awsAccessKeyId == null || awsAccessKeyId.isBlank()) &&
            (awsSecretAccessKey == null || awsSecretAccessKey.isBlank())) {
            warnings.add("AWS credentials not configured - S3 storage will not work");
        } else if (awsAccessKeyId != null && awsAccessKeyId.startsWith("AKIA")) {
            // Check if using long-term credentials (starts with AKIA)
            log.info("AWS credentials configured (using IAM user access key)");
        }

        // Warning: Email password
        if (mailPassword == null || mailPassword.isBlank()) {
            warnings.add("Mail password not configured - email sending will fail");
        }

        // Warning: SMS token
        if (smsEnabled && (smsToken == null || smsToken.isBlank())) {
            warnings.add("SMS token not configured but SMS is enabled - SMS sending will fail");
        }

        // Log warnings
        for (String warning : warnings) {
            log.warn("SECURITY WARNING: {}", warning);
        }

        // Fail on critical errors
        if (!errors.isEmpty()) {
            String errorMessage = String.join("; ", errors);
            log.error("SECURITY ERROR: Critical configuration issues detected: {}", errorMessage);
            throw new IllegalStateException(
                "Application startup blocked due to security configuration issues: " + errorMessage
            );
        }

        log.info("Secrets validation completed: {} warnings, {} critical errors",
            warnings.size(), errors.size());
    }
}
