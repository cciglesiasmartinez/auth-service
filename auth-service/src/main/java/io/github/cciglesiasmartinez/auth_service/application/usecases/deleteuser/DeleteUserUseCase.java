package io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.domain.exception.PasswordMismatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for deleting the user account.
 * <p>
 * Looks for the user in the database, and if the password matches, deletes it.
 */
@Slf4j
@Service
@AllArgsConstructor
public class DeleteUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user delete (self delete) use case.
     *
     * @param command {@link DeleteUserCommand} command containing user id and current password.
     * @throws UserNotFoundException if the user is not in the repository.
     * @throws PasswordMismatchException if the current password does not match the stored one.
     */
    public void execute(DeleteUserCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    log.warn("UserId {} not found in database.", command.userId());
                    return new UserNotFoundException();
                });
        user.validateCurrentPassword(currentPassword, passwordEncoder);
        log.info("Deleted their user successfully.");
        userRepository.delete(user);
    }

}
