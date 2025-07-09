package com.filmdb.auth.auth_service.application.event;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationEmailRequestedEvent {

    private final Email email;
    private final VerificationCodeString code;

}
