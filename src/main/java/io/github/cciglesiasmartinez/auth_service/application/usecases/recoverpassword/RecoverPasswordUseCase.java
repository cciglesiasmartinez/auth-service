package io.github.cciglesiasmartinez.auth_service.application.usecases.recoverpassword;

import io.github.cciglesiasmartinez.auth_service.application.event.RecoverCodeRequestedEvent;
import io.github.cciglesiasmartinez.auth_service.application.services.RecoverCodeService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RecoverCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.RecoverPasswordResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service for recovering the user password.
 * <p>
 * Creates a temporary {@link RecoverCode} instance and publishes a {@link RecoverCodeRequestedEvent}. It associates
 * a temporary recover code with the given email.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RecoverPasswordUseCase {

    private final UserRepository userRepository;
    private final RecoverCodeService recoverCodeService;
    private final ApplicationEventPublisher springPublisher;

    /**
     *  Executes the recover user password use case.
     *
     * @param command containing the user email.
     * @return a {@link RecoverPasswordResponse} instance containing the given mail and a message.
     * @throws UserNotFoundException if user does not exist.
     */
    public Envelope<RecoverPasswordResponse> execute(RecoverPasswordCommand command) {
        Email email = Email.of(command.email());
        if (!userRepository.existsByEmail(email)) {
            // TODO: Consider if it is appropriate throwing this exception
            log.warn("Email {} does not exist.", email.value());
            throw new UserNotFoundException();
        }
        RecoverCode recoverCode = recoverCodeService.generate(email, command.ip(), command.userAgent());
        springPublisher.publishEvent(new RecoverCodeRequestedEvent(email, recoverCode.recoverCodeString()));
        log.info("Recover password code requested for email {} successfully requested", email.value());
        RecoverPasswordResponse data = new RecoverPasswordResponse("recover_sent", email.value());
        return new Envelope<>(data, new Meta());
    }

}
