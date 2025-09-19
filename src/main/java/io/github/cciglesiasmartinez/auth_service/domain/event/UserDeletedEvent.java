package io.github.cciglesiasmartinez.auth_service.domain.event;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Event sent after a user is deleted.
 */
@Getter
public class UserDeletedEvent implements DomainEvent {

    private final UserId userId;
    private final LocalDateTime occurredOn;

    public UserDeletedEvent(UserId userId) {
        this.userId = userId;
        this.occurredOn = LocalDateTime.now();
    }

}
