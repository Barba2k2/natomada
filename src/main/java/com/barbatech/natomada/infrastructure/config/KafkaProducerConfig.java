package com.barbatech.natomada.infrastructure.config;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka producer configuration for publishing events
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Producer factory configuration
     */
    @Bean
    public ProducerFactory<String, BaseEvent> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        // Kafka broker address
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        // Key serializer (String)
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Value serializer (JSON)
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // Producer reliability settings
        config.put(ProducerConfig.ACKS_CONFIG, "all"); // Wait for all replicas (required for idempotence)
        config.put(ProducerConfig.RETRIES_CONFIG, 3); // Retry up to 3 times
        config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // Prevent duplicates

        // Compression
        config.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // Batching for better throughput
        config.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        config.put(ProducerConfig.LINGER_MS_CONFIG, 10);

        // JSON serializer settings - enable type info headers for polymorphic deserialization
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);

        return new DefaultKafkaProducerFactory<>(config);
    }

    /**
     * Kafka template for sending messages
     */
    @Bean
    public KafkaTemplate<String, BaseEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
