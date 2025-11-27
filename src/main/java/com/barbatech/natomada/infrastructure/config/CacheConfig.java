package com.barbatech.natomada.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis Cache Configuration
 *
 * Configures cache TTL (Time To Live) for different cache regions
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configure Redis Cache Manager with custom TTL per cache
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Create ObjectMapper with JSR310 (Java 8 Date/Time) support
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Default cache configuration (10 minutes)
        RedisCacheConfiguration defaultConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
            )
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer(objectMapper)
                )
            )
            .disableCachingNullValues();

        // Custom TTL per cache name
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // Nearby stations: 5 minutes (external APIs are slow, but data changes)
        cacheConfigurations.put(
            "nearby-stations",
            defaultConfig.entryTtl(Duration.ofMinutes(5))
        );

        // Station details: 30 minutes (details don't change often)
        cacheConfigurations.put(
            "station-details",
            defaultConfig.entryTtl(Duration.ofMinutes(30))
        );

        // User vehicles: 10 minutes
        cacheConfigurations.put(
            "user-vehicles",
            defaultConfig.entryTtl(Duration.ofMinutes(10))
        );

        return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(defaultConfig)
            .withInitialCacheConfigurations(cacheConfigurations)
            .build();
    }
}
