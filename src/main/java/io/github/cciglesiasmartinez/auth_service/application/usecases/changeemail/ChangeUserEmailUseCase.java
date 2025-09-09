package io.github.cciglesiasmartinez.auth_service.application.usecases.changeemail;

import io.github.cciglesiasmartinez.auth_service.domain.exception.EmailAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ChangeEmailResponse;
import io.github.cciglesiasmartinez.auth_service.domain.exception.PasswordMismatchException;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for changing user email.
 * <p>
 * Looks for the user in the repository and changes its email if provided password matches the stored one.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChangeUserEmailUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user change email user case.
     *
     * @param command Change email command containing user id, current password and the new email desired.
     * @return {@link ChangeEmailResponse} object containing the new email and the current timestamp.
     * @throws UserNotFoundException if current email is already in use.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     * @throws EmailAlreadyExistsException if email already exists.
     * @throws UserIsExternalException if user is externally authenticated.
     */
    public Envelope<ChangeEmailResponse> execute(ChangeUserEmailCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        Email newEmail = Email.of(command.newEmail());
        if (userRepository.existsByEmail(newEmail)) {
            String message = "Email " + newEmail.value() + " already exists.";
            throw new EmailAlreadyExistsException(message);
        }
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    String message = "UserId " + command.userId() + " not found in database.";
                    return new UserNotFoundException(message);
                });
        if (user.isExternal()) {
            String message = "External user " + user.id().value() + " tried to change their email unsuccessfully.";
            throw new UserIsExternalException(message);
        }
        user.changeEmail(currentPassword, newEmail, passwordEncoder);
        userRepository.save(user);
        log.info("Changed email to {} successfully.", newEmail);
        ChangeEmailResponse data = new ChangeEmailResponse(newEmail.value(), LocalDateTime.now());
        return new Envelope<>(data, new Meta());
    }

}
