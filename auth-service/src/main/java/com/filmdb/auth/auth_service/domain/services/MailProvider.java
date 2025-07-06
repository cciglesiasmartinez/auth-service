package com.filmdb.auth.auth_service.domain.services;

import com.filmdb.auth.auth_service.domain.model.valueobject.EmailMessage;

public interface MailProvider {

    void sendMail(EmailMessage email);

}
