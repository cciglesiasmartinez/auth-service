package com.filmdb.auth.auth_service.infrastructure.notification.mail;

import com.filmdb.auth.auth_service.domain.model.valueobject.EmailMessage;
import com.filmdb.auth.auth_service.domain.services.MailProvider;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SmtpMailer implements MailProvider {

    private final JavaMailSender mailSender;

    @Override
    public void sendMail(EmailMessage email) {
        try {
            System.out.println("Initializing sendMail() method");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);
            helper.setTo(email.to().value());
            helper.setSubject(email.subject());
            helper.setText(email.body());
            mailSender.send(message);
            System.out.println("Mail sended :)");
        } catch (MailException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
