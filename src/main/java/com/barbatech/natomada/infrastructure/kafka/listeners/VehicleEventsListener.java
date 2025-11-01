package com.barbatech.natomada.infrastructure.kafka.listeners;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import com.barbatech.natomada.infrastructure.events.cars.VehicleAddedEvent;
import com.barbatech.natomada.infrastructure.events.cars.VehicleImageUploadedEvent;
import com.barbatech.natomada.infrastructure.events.cars.VehicleRemovedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listener for vehicle events
 * Demonstrates:
 * - Analytics tracking
 * - Recommendations engine
 * - Data synchronization
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleEventsListener {

    @KafkaListener(
        topics = "natomada.vehicles.events",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleVehicleEvent(
        @Payload BaseEvent event,
        @Header(KafkaHeaders.RECEIVED_KEY) String key,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) long offset,
        Acknowledgment acknowledgment
    ) {
        try {
            log.info("Received event from topic 'natomada.vehicles.events': eventType={}, partition={}, offset={}, key={}",
                event.getEventType(), partition, offset, key);

            // Dispatch to specific handler based on event type
            switch (event) {
                case VehicleAddedEvent e -> handleVehicleAdded(e);
                case VehicleRemovedEvent e -> handleVehicleRemoved(e);
                case VehicleImageUploadedEvent e -> handleVehicleImageUploaded(e);
                default -> log.warn("Unknown event type: {}", event.getClass().getSimpleName());
            }

            acknowledgment.acknowledge();
            log.debug("Event acknowledged successfully");

        } catch (Exception e) {
            log.error("Error processing vehicle event: {}", e.getMessage(), e);
        }
    }

    private void handleVehicleAdded(VehicleAddedEvent event) {
        log.info("Processing VEHICLE_ADDED: userId={}, carName={}, nickname={}",
            event.getUserId(), event.getCarName(), event.getNickname());

        // TODO: Update user's vehicle count
        log.info("🚗 User {} added vehicle: {}", event.getUserId(), event.getCarName());

        // TODO: Trigger recommendations for compatible charging stations
        log.info("🔌 Finding compatible charging stations for: {}", event.getCarName());

        // TODO: Send push notification
        log.info("📱 Notification: Vehicle '{}' added successfully", event.getNickname());

        // TODO: Analytics - track popular car models
        log.info("📊 Analytics: Tracking vehicle model - {}", event.getCarName());
    }

    private void handleVehicleRemoved(VehicleRemovedEvent event) {
        log.info("Processing VEHICLE_REMOVED: userId={}, userVehicleId={}",
            event.getUserId(), event.getUserVehicleId());

        // TODO: Archive vehicle data
        log.info("📦 Archiving vehicle data for userVehicleId: {}", event.getUserVehicleId());

        // TODO: Update recommendations
        log.info("🔄 Updating charging station recommendations for userId: {}", event.getUserId());

        // TODO: Send confirmation notification
        log.info("✅ Vehicle removed successfully for userId: {}", event.getUserId());
    }

    private void handleVehicleImageUploaded(VehicleImageUploadedEvent event) {
        log.info("Processing VEHICLE_IMAGE_UPLOADED: userId={}, imageUrl={}, size={}KB",
            event.getUserId(), event.getImageUrl(), event.getFileSizeBytes() / 1024);

        // TODO: Generate thumbnail
        log.info("🖼 Generating thumbnail for image: {}", event.getImageUrl());

        // TODO: Scan for inappropriate content (moderation)
        log.info("🛡 Running content moderation for image: {}", event.getImageUrl());

        // TODO: Update vehicle profile completeness
        log.info("✨ Vehicle profile updated with image for userVehicleId: {}", event.getUserVehicleId());
    }
}
