package com.barbatech.natomada.infrastructure.ratelimit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Result of a rate limit check.
 */
@Getter
@AllArgsConstructor
public class RateLimitResult {

    private final boolean allowed;
    private final long remainingTokens;
    private final long retryAfterSeconds;

    public static RateLimitResult allowed(long remainingTokens) {
        return new RateLimitResult(true, remainingTokens, 0);
    }

    public static RateLimitResult blocked(long remainingTokens, long retryAfterSeconds) {
        return new RateLimitResult(false, remainingTokens, retryAfterSeconds);
    }
}
