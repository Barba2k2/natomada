package com.barbatech.natomada.infrastructure.events.auth;

import com.barbatech.natomada.infrastructure.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event published when a new user registers
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRegisteredEvent extends BaseEvent {

    private Long userId;
    private String name;
    private String email;
    private String phone;

    public static UserRegisteredEvent of(Long userId, String name, String email, String phone) {
        UserRegisteredEvent event = UserRegisteredEvent.builder()
            .userId(userId)
            .name(name)
            .email(email)
            .phone(phone)
            .build();

        event.initialize("USER_REGISTERED");
        return event;
    }
}
