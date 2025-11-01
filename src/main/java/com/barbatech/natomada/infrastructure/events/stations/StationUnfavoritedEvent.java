package com.barbatech.natomada.infrastructure.events.stations;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user removes a station from favorites
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StationUnfavoritedEvent extends BaseEvent {

    private Long userId;
    private Long stationId;

    public static StationUnfavoritedEvent of(Long userId, Long stationId) {
        StationUnfavoritedEvent event = StationUnfavoritedEvent.builder()
            .userId(userId)
            .stationId(stationId)
            .build();

        event.initialize("STATION_UNFAVORITED");
        return event;
    }
}
