package com.barbatech.natomada.infrastructure.ratelimit;

import com.barbatech.natomada.infrastructure.config.RateLimitProperties;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.distributed.proxy.ProxyManager;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.ByteArrayCodec;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.codec.StringCodec;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Service for managing rate limiting using Bucket4j with Redis backend.
 *
 * Provides distributed rate limiting across multiple application instances.
 * Falls back to in-memory rate limiting if Redis is unavailable.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final RateLimitProperties rateLimitProperties;

    @Value("${spring.data.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.data.redis.port:6379}")
    private int redisPort;

    @Value("${spring.data.redis.password:}")
    private String redisPassword;

    private ProxyManager<String> proxyManager;
    private RedisClient redisClient;
    private StatefulRedisConnection<String, byte[]> connection;
    private boolean redisAvailable = false;

    // Fallback in-memory buckets when Redis is unavailable
    private final Map<String, Bucket> localBuckets = new ConcurrentHashMap<>();

    // Error tracking for monitoring
    private volatile long rateLimitErrorCount = 0;
    private volatile long lastErrorLogTime = 0;
    private static final long ERROR_LOG_INTERVAL_MS = 60000; // Log errors at most once per minute

    @PostConstruct
    public void init() {
        try {
            String redisUri = redisPassword != null && !redisPassword.isEmpty()
                ? String.format("redis://%s@%s:%d", redisPassword, redisHost, redisPort)
                : String.format("redis://%s:%d", redisHost, redisPort);

            redisClient = RedisClient.create(redisUri);
            connection = redisClient.connect(RedisCodec.of(StringCodec.UTF8, ByteArrayCodec.INSTANCE));

            // Note: withExpirationStrategy is deprecated but the replacement API isn't available yet
            // This ensures bucket keys expire from Redis after 1 hour of inactivity
            @SuppressWarnings("deprecation")
            var builder = LettuceBasedProxyManager.builderFor(connection)
                .withExpirationStrategy(
                    io.github.bucket4j.distributed.ExpirationAfterWriteStrategy
                        .basedOnTimeForRefillingBucketUpToMax(Duration.ofHours(1))
                );
            proxyManager = builder.build();

            redisAvailable = true;
            log.info("Rate limiting initialized with Redis backend at {}:{}", redisHost, redisPort);
        } catch (Exception e) {
            log.warn("Failed to connect to Redis for rate limiting, using in-memory fallback: {}", e.getMessage());
            redisAvailable = false;
        }
    }

    @PreDestroy
    public void cleanup() {
        if (connection != null) {
            connection.close();
        }
        if (redisClient != null) {
            redisClient.shutdown();
        }
    }

    /**
     * Check if a login attempt is allowed for the given IP address.
     *
     * @param ipAddress the client IP address
     * @return RateLimitResult with allowed status and remaining tokens
     */
    public RateLimitResult checkLoginRateLimit(String ipAddress) {
        String key = "rate_limit:login:" + ipAddress;
        return checkRateLimit(key, () -> createLoginConfiguration());
    }

    /**
     * Check if an OTP request is allowed for the given identifier (phone or email).
     *
     * @param identifier the phone number or email address
     * @return RateLimitResult with allowed status and remaining tokens
     */
    public RateLimitResult checkOtpRateLimit(String identifier) {
        String key = "rate_limit:otp:" + identifier;
        return checkRateLimit(key, () -> createOtpConfiguration());
    }

    /**
     * Check if a general auth request is allowed for the given IP address.
     *
     * @param ipAddress the client IP address
     * @return RateLimitResult with allowed status and remaining tokens
     */
    public RateLimitResult checkAuthRateLimit(String ipAddress) {
        String key = "rate_limit:auth:" + ipAddress;
        return checkRateLimit(key, () -> createAuthConfiguration());
    }

    private RateLimitResult checkRateLimit(String key, Supplier<BucketConfiguration> configSupplier) {
        try {
            Bucket bucket = getBucket(key, configSupplier);
            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);

            if (probe.isConsumed()) {
                return RateLimitResult.allowed(probe.getRemainingTokens());
            } else {
                long waitTimeSeconds = probe.getNanosToWaitForRefill() / 1_000_000_000;
                return RateLimitResult.blocked(0, waitTimeSeconds);
            }
        } catch (Exception e) {
            rateLimitErrorCount++;
            long currentTime = System.currentTimeMillis();

            // Rate-limit error logging to avoid log flooding
            if (currentTime - lastErrorLogTime > ERROR_LOG_INTERVAL_MS) {
                log.error("Rate limit check failed for key {} (total errors: {}). " +
                    "Allowing request to avoid blocking legitimate users. Error: {}",
                    key, rateLimitErrorCount, e.getMessage());
                lastErrorLogTime = currentTime;
            }

            // SECURITY NOTE: Fail-open allows requests when rate limiting fails.
            // This is a trade-off between availability and security.
            // Monitor rateLimitErrorCount for signs of Redis issues.
            return RateLimitResult.allowed(1);
        }
    }

    /**
     * Returns the total count of rate limit errors since application startup.
     * Use this for monitoring and alerting.
     */
    public long getRateLimitErrorCount() {
        return rateLimitErrorCount;
    }

    /**
     * Returns whether Redis is currently available for rate limiting.
     */
    public boolean isRedisAvailable() {
        return redisAvailable;
    }

    private Bucket getBucket(String key, Supplier<BucketConfiguration> configSupplier) {
        if (redisAvailable && proxyManager != null) {
            return proxyManager.builder().build(key, configSupplier);
        }
        // Fallback to local bucket
        return localBuckets.computeIfAbsent(key, k -> Bucket.builder()
            .addLimit(configSupplier.get().getBandwidths()[0])
            .build());
    }

    private BucketConfiguration createLoginConfiguration() {
        RateLimitProperties.BucketConfig config = rateLimitProperties.getLogin();
        return BucketConfiguration.builder()
            .addLimit(Bandwidth.builder()
                .capacity(config.getCapacity())
                .refillGreedy(config.getRefillTokens(), Duration.ofMinutes(config.getRefillDurationMinutes()))
                .build())
            .build();
    }

    private BucketConfiguration createOtpConfiguration() {
        RateLimitProperties.BucketConfig config = rateLimitProperties.getOtp();
        return BucketConfiguration.builder()
            .addLimit(Bandwidth.builder()
                .capacity(config.getCapacity())
                .refillGreedy(config.getRefillTokens(), Duration.ofMinutes(config.getRefillDurationMinutes()))
                .build())
            .build();
    }

    private BucketConfiguration createAuthConfiguration() {
        RateLimitProperties.BucketConfig config = rateLimitProperties.getAuth();
        return BucketConfiguration.builder()
            .addLimit(Bandwidth.builder()
                .capacity(config.getCapacity())
                .refillGreedy(config.getRefillTokens(), Duration.ofMinutes(config.getRefillDurationMinutes()))
                .build())
            .build();
    }
}
