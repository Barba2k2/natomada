package com.barbatech.natomada.infrastructure.events.cars;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user uploads a vehicle image
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VehicleImageUploadedEvent extends BaseEvent {

    private Long userId;
    private Long userVehicleId;
    private String imageUrl;
    private Long fileSizeBytes;

    public static VehicleImageUploadedEvent of(Long userId, Long userVehicleId,
                                               String imageUrl, Long fileSizeBytes) {
        VehicleImageUploadedEvent event = VehicleImageUploadedEvent.builder()
            .userId(userId)
            .userVehicleId(userVehicleId)
            .imageUrl(imageUrl)
            .fileSizeBytes(fileSizeBytes)
            .build();

        event.initialize("VEHICLE_IMAGE_UPLOADED");
        return event;
    }
}
