package com.barbatech.natomada.infrastructure.kafka.listeners;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserLoggedInEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserLoggedOutEvent;
import com.barbatech.natomada.infrastructure.events.auth.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Listener for authentication events
 * Demonstrates different use cases:
 * - Logging for audit trail
 * - Sending welcome emails
 * - Analytics/metrics collection
 * Only enabled when kafka.enabled=true
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.enabled", havingValue = "true", matchIfMissing = false)
public class AuthEventsListener {

    /**
     * Process authentication events
     * Use case: Send welcome email, create analytics profile, track logins
     */
    @KafkaListener(
        topics = "natomada.auth.events",
        groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleAuthEvent(
        @Payload BaseEvent event,
        @Header(KafkaHeaders.RECEIVED_KEY) String key,
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Header(KafkaHeaders.OFFSET) long offset,
        Acknowledgment acknowledgment
    ) {
        try {
            log.info("Received event from topic 'natomada.auth.events': eventType={}, partition={}, offset={}, key={}",
                event.getEventType(), partition, offset, key);

            // Dispatch to specific handler based on event type
            switch (event) {
                case UserRegisteredEvent e -> handleUserRegistered(e);
                case UserLoggedInEvent e -> handleUserLoggedIn(e);
                case UserLoggedOutEvent e -> handleUserLoggedOut(e);
                default -> log.warn("Unknown event type: {}", event.getClass().getSimpleName());
            }

            // Acknowledge message processing
            acknowledgment.acknowledge();
            log.debug("Event acknowledged successfully");

        } catch (Exception e) {
            log.error("Error processing auth event: {}", e.getMessage(), e);
            // Don't acknowledge - message will be retried or sent to DLQ
        }
    }

    private void handleUserRegistered(UserRegisteredEvent event) {
        log.info("Processing USER_REGISTERED: userId={}, name={}, email={}",
            event.getUserId(), event.getName(), event.getEmail());

        // TODO: Send welcome email
        log.info("📧 Sending welcome email to: {}", event.getEmail());

        // TODO: Create analytics profile
        log.info("📊 Creating analytics profile for userId: {}", event.getUserId());

        // TODO: Send notification to admin dashboard
        log.info("🔔 Notifying admin: New user registered - {}", event.getName());
    }

    private void handleUserLoggedIn(UserLoggedInEvent event) {
        log.info("Processing USER_LOGGED_IN: userId={}, email={}, ip={}",
            event.getUserId(), event.getEmail(), event.getIpAddress());

        // TODO: Update last login timestamp
        log.info("⏰ Updating last login time for userId: {}", event.getUserId());

        // TODO: Check for suspicious activity (unusual IP, location)
        log.info("🔒 Security check: IP={}, UserAgent={}", event.getIpAddress(), event.getUserAgent());

        // TODO: Increment login counter for analytics
        log.info("📈 Incrementing login counter for userId: {}", event.getUserId());
    }

    private void handleUserLoggedOut(UserLoggedOutEvent event) {
        log.info("Processing USER_LOGGED_OUT: userId={}", event.getUserId());

        // TODO: Update session duration
        log.info("⏱ Recording session duration for userId: {}", event.getUserId());

        // TODO: Cleanup temporary data
        log.info("🧹 Cleaning up session data for userId: {}", event.getUserId());
    }
}
