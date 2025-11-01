package com.barbatech.natomada.infrastructure.kafka;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Service for publishing events to Kafka
 * Centralized event publishing with logging and error handling
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    /**
     * Publish event to Kafka topic
     *
     * @param topic Kafka topic name
     * @param key   Message key (for partitioning)
     * @param event Event to publish
     */
    public void publish(String topic, String key, BaseEvent event) {
        log.info("Publishing event to topic '{}': eventType={}, eventId={}",
            topic, event.getEventType(), event.getEventId());

        CompletableFuture<SendResult<String, BaseEvent>> future =
            kafkaTemplate.send(topic, key, event);

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
    }

    /**
     * Publish event without a specific key (round-robin partitioning)
     */
    public void publish(String topic, BaseEvent event) {
        publish(topic, event.getEventId(), event);
    }
}
