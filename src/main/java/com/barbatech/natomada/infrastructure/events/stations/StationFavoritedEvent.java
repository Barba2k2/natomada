package com.barbatech.natomada.infrastructure.events.stations;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user favorites a charging station
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StationFavoritedEvent extends BaseEvent {

    private Long userId;
    private Long stationId;
    private String stationName;
    private String stationAddress;

    public static StationFavoritedEvent of(Long userId, Long stationId,
                                           String stationName, String stationAddress) {
        StationFavoritedEvent event = StationFavoritedEvent.builder()
            .userId(userId)
            .stationId(stationId)
            .stationName(stationName)
            .stationAddress(stationAddress)
            .build();

        event.initialize("STATION_FAVORITED");
        return event;
    }
}
