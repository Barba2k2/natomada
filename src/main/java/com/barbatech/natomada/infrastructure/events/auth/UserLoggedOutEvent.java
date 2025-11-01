package com.barbatech.natomada.infrastructure.events.auth;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user logs out
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserLoggedOutEvent extends BaseEvent {

    private Long userId;

    public static UserLoggedOutEvent of(Long userId) {
        UserLoggedOutEvent event = UserLoggedOutEvent.builder()
            .userId(userId)
            .build();

        event.initialize("USER_LOGGED_OUT");
        return event;
    }
}
