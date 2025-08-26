package io.github.cciglesiasmartinez.auth_service.application.event;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.VerificationCodeString;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VerificationEmailRequestedEvent {

    private final Email email;
    private final VerificationCodeString code;

}
