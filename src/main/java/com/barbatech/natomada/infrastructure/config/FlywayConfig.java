package com.barbatech.natomada.infrastructure.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Flyway Configuration
 * Ensures database migrations are applied on application startup
 * This is a workaround for Spring Boot 4.0.0-SNAPSHOT auto-configuration issues
 */
@Slf4j
@Configuration
public class FlywayConfig {

    /**
     * Flyway Migrator Bean
     * Runs migrations immediately after DataSource is created
     * High priority order ensures this runs before other database-dependent beans
     */
    @Component
    @Order(1)
    @DependsOn({})
    public static class FlywayMigrator {

        private final DataSource dataSource;

        @Value("${spring.flyway.validate-on-migrate:true}")
        private boolean validateOnMigrate;

        public FlywayMigrator(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @PostConstruct
        public void migrate() {
            log.info("==========================================");
            log.info("Configuring Flyway for database migrations");
            log.info("==========================================");

            Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .validateOnMigrate(validateOnMigrate)
                .outOfOrder(false)
                .cleanDisabled(true)
                .load();

            try {
                log.info("Running Flyway migrations...");
                MigrateResult result = flyway.migrate();
                log.info("Flyway migrations completed successfully. {} migrations applied.", result.migrationsExecuted);
                log.info("Database is now at version: {}", result.targetSchemaVersion);
            } catch (Exception e) {
                log.error("Flyway migration failed: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to run database migrations", e);
            }
        }
    }

}
