package io.github.cciglesiasmartinez.auth_service.infrastructure.event;

import io.github.cciglesiasmartinez.auth_service.domain.event.UserRegisteredEvent;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EmailMessage;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.MailProvider;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.MessageBroker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserRegisteredEventListener {

    private final MailProvider mailProvider;
    private final MessageBroker messageBroker;

    @Async
    @EventListener
    public void handle(UserRegisteredEvent event) {
        log.info("Handling UserRegisteredEvent for user {}", event.getUserId().value());
        EmailMessage confirmationMail = EmailMessage.of(
                event.getUserEmail(),
                "User registration confirmation.",
                "Your user has been registered successfully.");
        mailProvider.sendMail(confirmationMail);
        String message = "User " + event.getUserId().value() + " registered.";
        messageBroker.sendMessage(message);
    }
}
