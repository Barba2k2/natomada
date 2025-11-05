package com.barbatech.natomada.infrastructure.seeders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Database Seeder - runs automatically on application startup
 * Populates initial data from CSV files
 */
@Slf4j
@Component
@Order(1) // Run early in the startup process
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final CarSeederService carSeederService;

    @Override
    public void run(String... args) {
        log.info("==========================================");
        log.info("🌱 Starting Database Seeders...");
        log.info("==========================================");

        try {
            // Seed cars from CSV
            carSeederService.seed();

            log.info("==========================================");
            log.info("✅ Database Seeding Completed Successfully");
            log.info("==========================================");

        } catch (Exception e) {
            log.error("==========================================");
            log.error("❌ Database Seeding Failed: {}", e.getMessage(), e);
            log.error("==========================================");
            // Don't throw - let the application start even if seeding fails
        }
    }
}
