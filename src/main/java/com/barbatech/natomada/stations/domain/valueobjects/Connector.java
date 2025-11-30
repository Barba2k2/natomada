package com.barbatech.natomada.stations.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Value Object: Connector
 *
 * Represents a charging connector with type-safe properties. Following Axel
 * Engineering Doctrine: explicit types over strings.
 *
 * Doctrine principles applied: - Predictability: No hidden behavior, clear
 * contract - Explicitness: Typed fields instead of String JSON - Domain Logic:
 * Business rules encapsulated in the value object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connector {

    private Long id;

    private String type;

    @JsonProperty("powerKW")
    private BigDecimal power;

    private Integer voltage;

    private Integer amps;

    @JsonProperty("currentType")
    private String current;

    private Integer quantity;

    private String status;

    @JsonProperty("is_operational")
    private Boolean isOperational;

    @JsonProperty("fastCharging")
    private Boolean fastCharging;

    @JsonProperty("ultraFastCharging")
    private Boolean ultraFastCharging;

    @JsonProperty("available")
    private Boolean available;

    /**
     * Domain logic: Check if connector is available
     *
     * Business rule: A connector is available if it's operational and has an
     * "Operational" status.
     *
     * @return true if connector is available for use
     */
    public boolean isAvailable() {
        return Boolean.TRUE.equals(isOperational)
                && "Operational".equalsIgnoreCase(status);
    }

    /**
     * Domain logic: Check if fast charging capable (>50kW)
     *
     * Business rule: Fast charging is defined as power >= 50kW
     *
     * @return true if connector supports fast charging
     */
    public boolean isFastCharging() {
        return power != null && power.compareTo(new BigDecimal("50")) >= 0;
    }

    /**
     * Domain logic: Check if ultra-fast charging capable (>150kW)
     *
     * Business rule: Ultra-fast charging is defined as power >= 150kW
     *
     * @return true if connector supports ultra-fast charging
     */
    public boolean isUltraFastCharging() {
        return power != null && power.compareTo(new BigDecimal("150")) >= 0;
    }
}
