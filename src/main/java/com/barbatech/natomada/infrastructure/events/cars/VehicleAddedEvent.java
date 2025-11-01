package com.barbatech.natomada.infrastructure.events.cars;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user adds a vehicle to their account
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleAddedEvent extends BaseEvent {

    private Long userId;
    private Long userVehicleId;
    private Long carId;
    private String carName;
    private String nickname;
    private String color;

    public static VehicleAddedEvent of(Long userId, Long userVehicleId, Long carId,
                                       String carName, String nickname, String color) {
        VehicleAddedEvent event = VehicleAddedEvent.builder()
            .userId(userId)
            .userVehicleId(userVehicleId)
            .carId(carId)
            .carName(carName)
            .nickname(nickname)
            .color(color)
            .build();

        event.initialize("VEHICLE_ADDED");
        return event;
    }
}
