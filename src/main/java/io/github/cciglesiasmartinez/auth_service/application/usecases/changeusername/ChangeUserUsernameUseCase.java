package io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UsernameAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ChangeUsernameResponse;
import io.github.cciglesiasmartinez.auth_service.domain.exception.PasswordMismatchException;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for changing user username.
 * <p>
 * Looks for the user in the repository and changes its username if provided password matches stored password.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChangeUserUsernameUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user change username use case.
     *
     * @param command Change username command containing user id, current password and the new username.
     * @return {@link ChangeUsernameResponse} object containing the new username and the current timestamp.
     * @throws UserNotFoundException if user does not exist in database.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     * @throws UsernameAlreadyExistsException if username already exists in database.
     */
    public Envelope<ChangeUsernameResponse> execute(ChangeUserUsernameCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        Username newUsername = Username.of(command.newUsername());
        if (userRepository.existsByUsername(newUsername)) {
            String message = "Username " + newUsername.value() + " already exists";
            throw new UsernameAlreadyExistsException(message);
        }
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    String message = "User " + command.userId() + " not found on the database.";
                    return new UserNotFoundException(message);
                });
        user.changeUsername(currentPassword, newUsername, passwordEncoder);
        userRepository.save(user);
        log.info("Changed username to {} successfully.", user.username().value());
        ChangeUsernameResponse data = new ChangeUsernameResponse(user.username().value(), LocalDateTime.now());
        return new Envelope<>(data, new Meta());
    }

}
