package com.filmdb.auth.auth_service.domain.port.out;

import com.filmdb.auth.auth_service.domain.model.valueobject.EmailMessage;

public interface MailProvider {

    void sendMail(EmailMessage email);

}
