package com.barbatech.natomada.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration properties for rate limiting.
 *
 * Rate limits are applied per IP address or identifier:
 * - Login: Strict limits to prevent brute force attacks
 * - OTP: Moderate limits to prevent SMS/email bombing
 * - Auth: General limits for all auth endpoints
 *
 * SECURITY: Configure trusted-proxies to prevent X-Forwarded-For spoofing.
 * Only IPs in the trusted-proxies list will have their forwarded headers honored.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rate-limit")
public class RateLimitProperties {

    private BucketConfig login = new BucketConfig(5, 5, 15);
    private BucketConfig otp = new BucketConfig(3, 3, 60);
    private BucketConfig auth = new BucketConfig(20, 20, 1);

    /**
     * Comma-separated list of trusted proxy IP addresses or CIDR ranges.
     * X-Forwarded-For headers are only trusted from these addresses.
     * Empty means trust no proxies (use direct client IP).
     * Example: "10.0.0.0/8,172.16.0.0/12,192.168.0.0/16,127.0.0.1"
     */
    private String trustedProxies = "";

    /**
     * Returns the list of trusted proxies parsed from the comma-separated string.
     */
    public List<String> getTrustedProxies() {
        if (trustedProxies == null || trustedProxies.isBlank()) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        for (String proxy : trustedProxies.split(",")) {
            String trimmed = proxy.trim();
            if (!trimmed.isEmpty()) {
                result.add(trimmed);
            }
        }
        return result;
    }

    @Data
    public static class BucketConfig {
        private int capacity;
        private int refillTokens;
        private int refillDurationMinutes;

        public BucketConfig() {
        }

        public BucketConfig(int capacity, int refillTokens, int refillDurationMinutes) {
            this.capacity = capacity;
            this.refillTokens = refillTokens;
            this.refillDurationMinutes = refillDurationMinutes;
        }
    }
}
