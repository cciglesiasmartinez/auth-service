package io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername;

import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ChangeUsernameResponse;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsNotExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UsernameAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for changing the username of an externally identified user.
 * <p>
 * Checks if the requested {@link Username} is available and if the {@link User} exists in our repository.
 * In that case, we proceed with the change and persists it in our repository.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ChangeExternalUserUsernameUseCase {

    private final UserRepository userRepository;

    /**
     * Executes the externally authenticated user (self) change username use case.
     *
     * @param command containing the new username and user id.
     * @return a {@link ChangeUsernameResponse} instance with the new username.
     * @throws UserNotFoundException if user is not on our repository.
     * @throws UserIsNotExternalException is user is not externally authenticated.
     * @throws UsernameAlreadyExistsException if requested username already exists in our repository.
     */
    public Envelope<ChangeUsernameResponse> execute(ChangeExternalUserUsernameCommand command) {
        Username newUsername = Username.of(command.newUsername());
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    String message = "User " + command.userId() + " not found on the database.";
                    return new UserNotFoundException(message);
                });
        if (!user.isExternal()) {
            String message = "User " + command.userId() + " is not external.";
            throw new UserIsNotExternalException(message);
        }
        if (userRepository.existsByUsername(newUsername)) {
            String message = "Username " + newUsername.value() + " already exists.";
            throw new UsernameAlreadyExistsException(message);
        }
        user.changeUsernameForExternalUser(newUsername);
        userRepository.save(user);
        log.info("Changed username to {} successfully.", user.username().value());
        ChangeUsernameResponse data = new ChangeUsernameResponse(user.username().value(), LocalDateTime.now());
        return new Envelope<>(data, new Meta());
    }

}
