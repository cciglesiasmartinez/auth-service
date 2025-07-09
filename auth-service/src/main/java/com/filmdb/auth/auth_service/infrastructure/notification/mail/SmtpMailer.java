package com.filmdb.auth.auth_service.infrastructure.notification.mail;

import com.filmdb.auth.auth_service.domain.model.valueobject.EmailMessage;
import com.filmdb.auth.auth_service.domain.services.MailProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class SmtpMailer implements MailProvider {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(EmailMessage email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setTo(email.to().value());
            helper.setSubject(email.subject());
            helper.setText(email.body());
            mailSender.send(message);
            log.info("Mail sent to {}.", email.to().value());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
