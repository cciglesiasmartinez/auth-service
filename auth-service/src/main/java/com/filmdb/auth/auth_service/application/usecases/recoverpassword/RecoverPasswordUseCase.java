package com.filmdb.auth.auth_service.application.usecases.recoverpassword;

import com.filmdb.auth.auth_service.application.event.RecoverCodeRequestedEvent;
import com.filmdb.auth.auth_service.application.services.RecoverCodeService;
import com.filmdb.auth.auth_service.domain.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.domain.model.RecoverCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.port.out.UserRepository;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.RecoverPasswordResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service for recovering user password.
 * <p>
 * TODO: Explain it further.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RecoverPasswordUseCase {

    private final UserRepository userRepository;
    private final RecoverCodeService recoverCodeService;
    private final ApplicationEventPublisher springPublisher;

    /**
     *
     * @param command
     * @throws UserNotFoundException
     * @return
     */
    public Envelope<RecoverPasswordResponse> execute(RecoverPasswordCommand command) {
        Email email = Email.of(command.email());
        if (!userRepository.existsByEmail(email)) {
            // TODO: Consider if it is apropiate throwing this exception
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
