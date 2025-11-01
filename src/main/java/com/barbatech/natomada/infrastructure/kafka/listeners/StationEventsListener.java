package com.barbatech.natomada.infrastructure.kafka.listeners;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import com.barbatech.natomada.infrastructure.events.stations.StationFavoritedEvent;
import com.barbatech.natomada.infrastructure.events.stations.StationUnfavoritedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listener for charging station events
 * Demonstrates:
 * - Popularity tracking
 * - Personalized recommendations
 * - Analytics and insights
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class StationEventsListener {

    @KafkaListener(
        topics = "natomada.stations.events",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleStationEvent(
        @Payload BaseEvent event,
        @Header(KafkaHeaders.RECEIVED_KEY) String key,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) long offset,
        Acknowledgment acknowledgment
    ) {
        try {
            log.info("Received event from topic 'natomada.stations.events': eventType={}, partition={}, offset={}, key={}",
                event.getEventType(), partition, offset, key);

            // Dispatch to specific handler based on event type
            switch (event) {
                case StationFavoritedEvent e -> handleStationFavorited(e);
                case StationUnfavoritedEvent e -> handleStationUnfavorited(e);
                default -> log.warn("Unknown event type: {}", event.getClass().getSimpleName());
            }

            acknowledgment.acknowledge();
            log.debug("Event acknowledged successfully");

        } catch (Exception e) {
            log.error("Error processing station event: {}", e.getMessage(), e);
        }
    }

    private void handleStationFavorited(StationFavoritedEvent event) {
        log.info("Processing STATION_FAVORITED: userId={}, stationId={}, stationName={}",
            event.getUserId(), event.getStationId(), event.getStationName());

        // TODO: Increment station popularity counter
        log.info("⭐ Incrementing popularity for station: {} (ID: {})",
            event.getStationName(), event.getStationId());

        // TODO: Update recommendations for user
        log.info("🎯 Updating personalized recommendations for userId: {}", event.getUserId());

        // TODO: Send notification when station has updates (new chargers, prices, etc)
        log.info("🔔 Subscribing user {} to updates for station: {}",
            event.getUserId(), event.getStationName());

        // TODO: Analytics - track popular locations/regions
        log.info("📍 Analytics: Tracking favorite location - {}", event.getStationAddress());
    }

    private void handleStationUnfavorited(StationUnfavoritedEvent event) {
        log.info("Processing STATION_UNFAVORITED: userId={}, stationId={}",
            event.getUserId(), event.getStationId());

        // TODO: Decrement station popularity counter
        log.info("⭐ Decrementing popularity for stationId: {}", event.getStationId());

        // TODO: Update recommendations
        log.info("🔄 Refreshing recommendations for userId: {}", event.getUserId());

        // TODO: Unsubscribe from station notifications
        log.info("🔕 Unsubscribing user {} from station updates", event.getUserId());
    }
}
