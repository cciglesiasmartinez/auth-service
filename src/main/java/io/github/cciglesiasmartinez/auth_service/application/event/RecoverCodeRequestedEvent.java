package io.github.cciglesiasmartinez.auth_service.application.event;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event triggered when a recover code is issued.
 * <p></p>
 * <ul>
 *     <li>User{@link Email} address.</li>
 *     <li>The issued {@link RecoverCodeString} code.</li>
 * </ul>
 */
@Getter
@AllArgsConstructor
public class RecoverCodeRequestedEvent {

    private final Email email;
    private final RecoverCodeString code;

}
