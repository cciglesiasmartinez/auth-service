package io.github.cciglesiasmartinez.auth_service.application.usecases.changepassword;

import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ChangePasswordResponse;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.domain.exception.PasswordMismatchException;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for changing user password.
 * <p>
 * Looks for the user in the repository and changes its password if the provided currentPassword matches
 * the user actual password.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChangePasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user change password use case.
     *
     * @param command Registration command containing user id, current password and the new desired password.
     * @return {@link ChangePasswordResponse} object with message and timestamp.
     * @throws UserNotFoundException if the user is not found on the repository.
     * @throws PasswordMismatchException if the current password does not match the stored password.
     * @throws UserIsExternalException if user is externally authenticated.
     */
    public Envelope<ChangePasswordResponse> execute(ChangePasswordCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        PlainPassword newPassword = PlainPassword.of(command.newPassword());
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    log.warn("UserId {} not found on database.", command.userId());
                    return new UserNotFoundException();
                });
        if (user.isExternal()) {
            log.warn("External user {} tried to change their password unsuccessfully.", user.id().value());
            throw new UserIsExternalException();
        }
        user.changePassword(currentPassword, newPassword, passwordEncoder);
        userRepository.save(user);
        log.info("Password changed successfully.");
        ChangePasswordResponse data = new ChangePasswordResponse("password_changed", LocalDateTime.now());
        return new Envelope<>(data, new Meta());
    }

}
