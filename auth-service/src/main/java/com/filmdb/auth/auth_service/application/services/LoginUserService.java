package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.LoginUserCommand;
import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.LoginResponse;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service for login a user.
 * <p>
 * Looks for the user in the database, if it exists, checks if the provided password matches the stored one.
 * If everything is correct, generates a token, and issues a {@link LoginResponse} object issuing the token.
 */
@Service
@AllArgsConstructor
public class LoginUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * Executes the user login use case.
     *
     * @param command {@link LoginUserCommand} command containing email and password.
     * @return {@link LoginResponse} object containing jwt token, expiration time and username.
     * @throws RuntimeException if the user is not found.
     * @throws PasswordMismatchException if provided password does not match stored password.
     */
    public LoginResponse execute(LoginUserCommand command) {
        Email email = Email.of(command.email());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials."));
        PlainPassword plainPassword = PlainPassword.of(command.password());
        user.validateCurrentPassword(plainPassword, passwordEncoder);
        String token = tokenProvider.generateToken(user.username().value());
        long expiresIn = tokenProvider.getTokenExpirationInSeconds();
        // Change username type from String to Username before completing migration
        return new LoginResponse(token, expiresIn, user.username().value());
    }

}
