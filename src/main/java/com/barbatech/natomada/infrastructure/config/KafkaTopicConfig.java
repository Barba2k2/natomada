package com.barbatech.natomada.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka topic configuration
 * Creates topics automatically on application startup
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Topic for authentication events (login, logout, register)
     */
    @Bean
    public NewTopic authEventsTopic() {
        return TopicBuilder.name("natomada.auth.events")
            .partitions(3) // 3 partitions for parallel processing
            .replicas(1)   // 1 replica (sufficient for dev/single broker)
            .compact()     // Enable log compaction
            .build();
    }

    /**
     * Topic for vehicle events (add, remove, update)
     */
    @Bean
    public NewTopic vehicleEventsTopic() {
        return TopicBuilder.name("natomada.vehicles.events")
            .partitions(3)
            .replicas(1)
            .compact()
            .build();
    }

    /**
     * Topic for station events (favorite, unfavorite)
     */
    @Bean
    public NewTopic stationEventsTopic() {
        return TopicBuilder.name("natomada.stations.events")
            .partitions(3)
            .replicas(1)
            .compact()
            .build();
    }

    /**
     * Dead Letter Queue topic for failed event processing
     */
    @Bean
    public NewTopic dlqTopic() {
        return TopicBuilder.name("natomada.dlq")
            .partitions(1)
            .replicas(1)
            .build();
    }
}
