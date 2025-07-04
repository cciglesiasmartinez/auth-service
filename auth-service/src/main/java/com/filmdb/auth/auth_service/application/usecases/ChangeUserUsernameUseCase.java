package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.application.commands.ChangeUserUsernameCommand;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.application.exception.UsernameAlreadyExistsException;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.ChangeUsernameResponse;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
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
    public ChangeUsernameResponse execute(ChangeUserUsernameCommand command) {
        // TODO: try-catch for log.warn?
        // TODO: Consider how to handle username changes for externally authenticated users.
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        Username newUsername = Username.of(command.newUsername());
        if (userRepository.existsByUsername(newUsername)) {
            log.warn("Username {} already exists.", newUsername.value());
            throw new UsernameAlreadyExistsException();
        }
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    log.warn("User {} not found on the database.", command.userId());
                    return new UserNotFoundException();
                });
        user.changeUsername(currentPassword, newUsername, passwordEncoder);
        userRepository.save(user);
        log.info("Changed username to {} successfully.", user.username().value());
        return new ChangeUsernameResponse(user.username().value(), LocalDateTime.now());
    }

}
