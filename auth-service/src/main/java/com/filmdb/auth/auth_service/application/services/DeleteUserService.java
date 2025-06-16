package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.DeleteUserCommand;
import com.filmdb.auth.auth_service.domain.model.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.exceptions.PasswordMismatchException;
import lombok.AllArgsConstructor;

/**
 * Application service for deleting the user account.
 * <p>
 * Looks for the user in the database, and if the password matches, deletes it.
 */
@AllArgsConstructor
public class DeleteUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user delete (self delete) use case.
     *
     * @param command {@link DeleteUserCommand} command containing user id and current password.
     * @throws RuntimeException if the user is not in the repository.
     * @throws PasswordMismatchException if the current password does not match the stored one.
     */
    public void execute(DeleteUserCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.validateCurrentPassword(currentPassword, passwordEncoder);
        userRepository.delete(user);
    }

}
