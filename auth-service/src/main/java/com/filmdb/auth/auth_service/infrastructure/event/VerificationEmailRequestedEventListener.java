package com.filmdb.auth.auth_service.infrastructure.event;

import com.filmdb.auth.auth_service.application.event.VerificationEmailRequestedEvent;
import com.filmdb.auth.auth_service.domain.model.valueobject.EmailMessage;
import com.filmdb.auth.auth_service.domain.services.MailProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class VerificationEmailRequestedEventListener {

    private final MailProvider mailProvider;

    @Async
    @EventListener
    public void handle(VerificationEmailRequestedEvent event) {
        log.info("Handling VerificationEmailRequestedEvent for email {} ", event.getEmail().value());
        EmailMessage verificationEmail = EmailMessage.of(
                event.getEmail(),
                "Your verification link",
                "https://bb72-5-159-172-11.ngrok-free.app/auth/register/verify?code="
                        + event.getCode().value()
        );
        mailProvider.sendMail(verificationEmail);
    }

}
