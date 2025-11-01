package com.barbatech.natomada.infrastructure.events.cars;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user removes a vehicle from their account
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleRemovedEvent extends BaseEvent {

    private Long userId;
    private Long userVehicleId;
    private Long carId;

    public static VehicleRemovedEvent of(Long userId, Long userVehicleId, Long carId) {
        VehicleRemovedEvent event = VehicleRemovedEvent.builder()
            .userId(userId)
            .userVehicleId(userVehicleId)
            .carId(carId)
            .build();

        event.initialize("VEHICLE_REMOVED");
        return event;
    }
}
