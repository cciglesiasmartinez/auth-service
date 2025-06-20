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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for changing user username.
 * <p>
 * Looks for the user in the repository and changes its username if provided password matches stored password.
 */
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
     * @throws UserNotFoundException if current username is already in use.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     */
    public ChangeUsernameResponse execute(ChangeUserUsernameCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        Username newUsername = Username.of(command.newUsername());
        if (userRepository.existsByUsername(newUsername)) {
            throw new UsernameAlreadyExistsException();
        }
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> new UserNotFoundException());
        user.changeUsername(currentPassword, newUsername, passwordEncoder);
        userRepository.save(user);
        return new ChangeUsernameResponse(user.username().value(), LocalDateTime.now());
    }

}
