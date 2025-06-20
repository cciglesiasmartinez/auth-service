package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.ChangePasswordResponse;
import com.filmdb.auth.auth_service.application.commands.ChangePasswordCommand;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for changing user password.
 * <p>
 * Looks for the user in the repository and changes its password if the provided currentPassword matches
 * the user actual password.
 */
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
     */
    public ChangePasswordResponse execute(ChangePasswordCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        PlainPassword newPassword = PlainPassword.of(command.newPassword());
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> new UserNotFoundException());
        user.changePassword(currentPassword, newPassword, passwordEncoder);
        userRepository.save(user);
        return new ChangePasswordResponse("Password changed.", LocalDateTime.now());
    }

}
