package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.RegisterUserCommand;
import com.filmdb.auth.auth_service.application.exception.UserAlreadyRegisteredException;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service for registering new users.
 * <p>
 * Validates that the user does not already exist (by email or username), encodes the password, creates the user,
 * and persists it using the domain repository.
 */
@Service
@AllArgsConstructor
public class RegisterUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the user registration use case.
     *
     * @param command The registration command containing username, email, and raw password.
     * @return A {@link UserResponse} representing the newly registered user.
     * @throws UserAlreadyRegisteredException if a user with the same email or username already exists.
     */
    public UserResponse execute(RegisterUserCommand command) {
        Username username = Username.of(command.username());
        Email email = Email.of(command.email());
        PlainPassword plainPassword = PlainPassword.of(command.password());
        if (userRepository.existsByEmailOrUsername(email, username)) {
            throw new UserAlreadyRegisteredException();
        }
        User user = User.create(username, email, plainPassword, passwordEncoder);
        userRepository.save(user);
        return UserResponse.fromDomainUser(user);
    }

}
