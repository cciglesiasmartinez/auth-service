package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.application.commands.DeleteUserCommand;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service for deleting the user account.
 * <p>
 * Looks for the user in the database, and if the password matches, deletes it.
 */
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
                .orElseThrow(() -> new UserNotFoundException());
        user.validateCurrentPassword(currentPassword, passwordEncoder);
        userRepository.delete(user);
    }

}
