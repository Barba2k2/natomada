package com.barbatech.natomada.infrastructure.kafka;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service for publishing events to Kafka
 * Centralized event publishing with logging and error handling
 * When Kafka is disabled, events are logged but not published
 */
@Slf4j
@Service
public class EventPublisher {

    private final Optional<KafkaTemplate<String, BaseEvent>> kafkaTemplate;

    @Autowired
    public EventPublisher(Optional<KafkaTemplate<String, BaseEvent>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        if (kafkaTemplate.isEmpty()) {
            log.info("Kafka is disabled - EventPublisher will log events without publishing");
        }
    }

    /**
     * Publish event to Kafka topic
     *
     * @param topic Kafka topic name
     * @param key   Message key (for partitioning)
     * @param event Event to publish
     */
    public void publish(String topic, String key, BaseEvent event) {
        if (kafkaTemplate.isEmpty()) {
            log.debug("Kafka disabled - Event not published: topic={}, eventType={}, eventId={}",
                topic, event.getEventType(), event.getEventId());
            return;
        }

        try {
            log.info("Publishing event to topic '{}': eventType={}, eventId={}",
                topic, event.getEventType(), event.getEventId());

            CompletableFuture<SendResult<String, BaseEvent>> future =
                kafkaTemplate.get().send(topic, key, event);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Event published successfully: topic={}, partition={}, offset={}",
                        topic,
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                    );
                } else {
                    log.error("Failed to publish event to topic '{}': eventType={}, error={}",
                        topic, event.getEventType(), ex.getMessage(), ex);
                }
            });
        } catch (Exception e) {
            log.error("Failed to publish event to Kafka (broker unavailable): topic={}, eventType={}, error={}",
                topic, event.getEventType(), e.getMessage());
            // Don't rethrow - allow the transaction to continue even if Kafka is unavailable
        }
    }

    /**
     * Publish event without a specific key (round-robin partitioning)
     */
    public void publish(String topic, BaseEvent event) {
        publish(topic, event.getEventId(), event);
    }
}
