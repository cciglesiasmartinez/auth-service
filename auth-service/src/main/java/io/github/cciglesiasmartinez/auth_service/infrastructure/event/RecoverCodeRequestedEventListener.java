package io.github.cciglesiasmartinez.auth_service.infrastructure.event;

import io.github.cciglesiasmartinez.auth_service.application.event.RecoverCodeRequestedEvent;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EmailMessage;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.MailProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class RecoverCodeRequestedEventListener {

    private final MailProvider mailProvider;

    @Async
    @EventListener
    public void handle(RecoverCodeRequestedEvent event) {
        log.info("Handling RecoverCodeRequestedEvent for email {}", event.getEmail().value());
        EmailMessage recoverPasswordEmail = EmailMessage.of(
                event.getEmail(),
                "Your recover code",
                "Your recover code is " + event.getCode().value()
        );
        mailProvider.sendMail(recoverPasswordEmail);
    }

}
