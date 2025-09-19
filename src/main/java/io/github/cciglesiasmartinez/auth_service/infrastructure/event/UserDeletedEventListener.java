package io.github.cciglesiasmartinez.auth_service.infrastructure.event;

import io.github.cciglesiasmartinez.auth_service.domain.event.UserDeletedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserDeletedEventListener {

    @Async
    @EventListener
    public void handle(UserDeletedEvent event) {
        log.info("Handling UserDeletedEvent for user {}", event.getUserId().value());
    }

}
