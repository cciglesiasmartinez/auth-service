package io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsNotExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for deleting a user that's externally identified.
 * <p>
 * This service basically verifies if a {@link User} exist for the {@link UserId} provided and if it is externally
 * authenticated through the {@code isExternal} attribute. This use case is needed since we can't have the password
 * for an externally authenticated user, which is a requirement for an internally identified user.
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeleteExternalUserUseCase {

    private final UserRepository userRepository;

    /**
     * Executes the externally identified user self delete use case.
     *
     * @param command only containing user id for the actual user.
     * @throws UserNotFoundException if user is not found on our repository.
     * @throws UserIsNotExternalException is user is not externally authenticated.
     */
    public void execute(DeleteExternalUserCommand command) {
        UserId userId = UserId.of(command.userId());
        User user = userRepository.findById(userId).
                orElseThrow(() -> {
                    String message = "UserID " + command.userId() + " not found in database";
                    return new UserNotFoundException(message);
                });
        if (!user.isExternal()) {
            String message = "User " + command.userId() + " is not external.";
            throw new UserIsNotExternalException(message);
        }
        userRepository.delete(user);
        log.info("Deleted their user successfully.");
    }

}
