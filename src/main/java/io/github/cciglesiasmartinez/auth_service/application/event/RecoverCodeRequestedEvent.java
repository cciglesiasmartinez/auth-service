package io.github.cciglesiasmartinez.auth_service.application.event;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecoverCodeRequestedEvent {

    private final Email email;
    private final RecoverCodeString code;

}
