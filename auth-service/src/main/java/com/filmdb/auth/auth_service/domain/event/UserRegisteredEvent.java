package com.filmdb.auth.auth_service.domain.event;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserRegisteredEvent implements DomainEvent {

    private final UserId userId;
    private final Email userEmail;
    private final LocalDateTime occurredOn;

    public UserRegisteredEvent(UserId userId, Email userEmail) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.occurredOn = LocalDateTime.now();
    }

}
