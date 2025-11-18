package com.barbatech.natomada.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Database configuration to ensure Flyway migrations run on startup
 */
@Configuration
public class DatabaseConfig {

    /**
     * Ensures Flyway migrations are applied on application startup
     * This strategy will migrate the database to the latest version
     */
    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            // Migrate to latest version
            flyway.migrate();
        };
    }
}
