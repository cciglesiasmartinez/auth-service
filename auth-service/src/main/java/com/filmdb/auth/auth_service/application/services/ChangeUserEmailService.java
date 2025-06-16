package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.ChangeUserEmailCommand;
import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.dto.responses.ChangeEmailResponse;
import com.filmdb.auth.auth_service.exceptions.PasswordMismatchException;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * Application service for changing user email.
 * <p>
 * Looks for the user in the repository and changes its email if provided password matches the stored one.
 */
@AllArgsConstructor
public class ChangeUserEmailService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user change email user case.
     *
     * @param command Change email command containing user id, current password and the new email desired.
     * @return {@link ChangeEmailResponse} object containing the new email and the current timestamp.
     * @throws RuntimeException if current email is already in use.
     * @throws PasswordMismatchException if provided password does not match the stored one.
     */
    public ChangeEmailResponse execute(ChangeUserEmailCommand command) {
        PlainPassword currentPassword = PlainPassword.of(command.currentPassword());
        Email newEmail = Email.of(command.newEmail());
        if (userRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email already in use.");
        }
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.changeEmail(currentPassword, newEmail, passwordEncoder);
        userRepository.save(user);
        return new ChangeEmailResponse(newEmail.value(), LocalDateTime.now());
    }

}
