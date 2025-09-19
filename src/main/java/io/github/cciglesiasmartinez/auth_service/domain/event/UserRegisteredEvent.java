package io.github.cciglesiasmartinez.auth_service.domain.event;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Event sent after a registration process is completed.
 */
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
