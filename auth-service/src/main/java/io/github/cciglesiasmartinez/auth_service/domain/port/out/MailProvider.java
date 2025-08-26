package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EmailMessage;

public interface MailProvider {

    void sendMail(EmailMessage email);

}
