package com.barbatech.natomada.infrastructure.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for all domain events
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent {

    /**
     * Unique event ID
     */
    private String eventId;

    /**
     * Event type (e.g., USER_REGISTERED, VEHICLE_ADDED)
     */
    private String eventType;

    /**
     * When the event occurred
     */
    private LocalDateTime timestamp;

    /**
     * Who/what triggered the event
     */
    private String source;

    /**
     * Event version for compatibility
     */
    private String version;

    /**
     * Initialize event with default values
     */
    public void initialize(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
        this.source = "natomada-api";
        this.version = "1.0";
    }
}
