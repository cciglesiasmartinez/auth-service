package io.github.cciglesiasmartinez.auth_service.application.event;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.VerificationCodeString;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Event triggered after a verification code for user registration is requested.
 * <p></p>
 * <ul>
 *     <li>User{@link Email} address.</li>
 *     <li>The issued {@link VerificationCodeString} code.</li>
 * </ul>
 */
@Getter
@AllArgsConstructor
public class VerificationEmailRequestedEvent {

    private final Email email;
    private final VerificationCodeString code;

}
