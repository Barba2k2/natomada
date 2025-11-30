package com.barbatech.natomada.stations.infrastructure.scheduler;

import com.barbatech.natomada.stations.application.services.StationsService;
import com.barbatech.natomada.stations.domain.entities.Station;
import com.barbatech.natomada.stations.infrastructure.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Scheduler to sync station data from external APIs
 * Runs every 3 days to keep station data fresh
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StationSyncScheduler {

    private final StationRepository stationRepository;
    private final StationsService stationsService;

    /**
     * Sync stale stations (older than 3 days)
     * Runs every day at 3 AM
     */
    @Scheduled(cron = "0 0 3 * * *") // 3 AM every day
    public void syncStaleStations() {
        log.info("Starting scheduled station sync...");

        LocalDateTime thresholdDate = LocalDateTime.now().minus(3, ChronoUnit.DAYS);

        // Find stations that haven't been synced in the last 3 days
        List<Station> staleStations = stationRepository.findAll().stream()
            .filter(station -> {
                if (station.getLastSyncAt() == null) {
                    return true; // Never synced
                }
                return station.getLastSyncAt().isBefore(thresholdDate);
            })
            .toList();

        log.info("Found {} stale stations to sync (threshold: {})", staleStations.size(), thresholdDate);

        int successCount = 0;
        int errorCount = 0;

        for (Station station : staleStations) {
            try {
                // Re-fetch and update station
                Station updated = stationsService.getOrFetchStationByOcmId(station.getOcmId());
                log.debug("Synced station: {} (last sync: {})", updated.getName(), updated.getLastSyncAt());
                successCount++;

                // Add a small delay to avoid rate limiting
                Thread.sleep(100); // 100ms delay between requests
            } catch (Exception e) {
                log.error("Error syncing station {}: {}", station.getOcmId(), e.getMessage());
                errorCount++;
            }
        }

        log.info("Station sync completed: {} successful, {} errors", successCount, errorCount);
    }

    /**
     * Clean up very old stations (older than 30 days with no favorites)
     * Runs every week on Sunday at 2 AM
     */
    @Scheduled(cron = "0 0 2 * * SUN") // 2 AM every Sunday
    public void cleanupOldStations() {
        log.info("Starting cleanup of old stations...");

        LocalDateTime threshold = LocalDateTime.now().minus(30, ChronoUnit.DAYS);

        // Find stations older than 30 days that have no favorites
        List<Station> oldStations = stationRepository.findAll().stream()
            .filter(station -> {
                if (station.getLastSyncAt() == null) {
                    return false;
                }
                return station.getLastSyncAt().isBefore(threshold);
            })
            .toList();

        int deletedCount = 0;

        for (Station station : oldStations) {
            try {
                // Only delete if no favorites exist for this station
                // This check should be added if needed
                // For now, we'll keep all stations
                log.debug("Skipping cleanup for station: {}", station.getName());
            } catch (Exception e) {
                log.error("Error during cleanup for station {}: {}", station.getOcmId(), e.getMessage());
            }
        }

        log.info("Cleanup completed: {} stations deleted", deletedCount);
    }
}
