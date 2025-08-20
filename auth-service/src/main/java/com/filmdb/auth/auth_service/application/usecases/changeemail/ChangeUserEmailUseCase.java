package com.filmdb.auth.auth_service.application.usecases.changeemail;

import com.filmdb.auth.auth_service.domain.exception.EmailAlreadyExistsException;
import com.filmdb.auth.auth_service.domain.exception.UserIsExternalException;
import com.filmdb.auth.auth_service.domain.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.port.out.UserRepository;
import com.filmdb.auth.auth_service.domain.port.out.PasswordEncoder;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.ChangeEmailResponse;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
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
            log.warn("Email {} already exists.", newEmail.value());
            throw new EmailAlreadyExistsException();
        }
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    log.warn("UserId {} not found in database.", command.userId());
                    return new UserNotFoundException();
                });
        if (user.isExternal()) {
            log.warn("External user {} tried to change their email unsuccessfully.", user.id().value());
            throw new UserIsExternalException();
        }
        user.changeEmail(currentPassword, newEmail, passwordEncoder);
        userRepository.save(user);
        log.info("Changed email to {} successfully.", newEmail);
        ChangeEmailResponse data = new ChangeEmailResponse(newEmail.value(), LocalDateTime.now());
        return new Envelope<>(data, new Meta());
    }

}
