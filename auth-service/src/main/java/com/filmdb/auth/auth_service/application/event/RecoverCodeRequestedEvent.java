package com.filmdb.auth.auth_service.application.event;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecoverCodeRequestedEvent {

    private final Email email;
    private final RecoverCodeString code;

}
