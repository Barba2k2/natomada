package com.barbatech.natomada.infrastructure.events.auth;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a user logs in
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserLoggedInEvent extends BaseEvent {

    private Long userId;
    private String email;
    private String ipAddress;
    private String userAgent;

    public static UserLoggedInEvent of(Long userId, String email, String ipAddress, String userAgent) {
        UserLoggedInEvent event = UserLoggedInEvent.builder()
            .userId(userId)
            .email(email)
            .ipAddress(ipAddress)
            .userAgent(userAgent)
            .build();

        event.initialize("USER_LOGGED_IN");
        return event;
    }
}
