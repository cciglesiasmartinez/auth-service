package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.application.commands.LoginUserCommand;
import com.filmdb.auth.auth_service.application.exception.InvalidCredentialsException;
import com.filmdb.auth.auth_service.application.services.RefreshTokenService;
import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.LoginResponse;
import com.filmdb.auth.auth_service.domain.exception.PasswordMismatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for login a user.
 * <p>
 * Looks for the user in the database, if it exists, checks if the provided password matches the stored one.
 * If everything is correct, generates a token, and issues a {@link LoginResponse} object issuing the token.
 */
@Slf4j
@Service
@AllArgsConstructor
public class LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * Executes the user login use case.
     *
     * @param command {@link LoginUserCommand} command containing email and password and context data (ip, userAgent).
     * @return {@link LoginResponse} object containing jwt token, expiration time and username.
     * @throws InvalidCredentialsException if the user is not found.
     * @throws PasswordMismatchException if provided password does not match stored password.
     */
    public LoginResponse execute(LoginUserCommand command) {
        Email email = Email.of(command.email());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found for email {}", email.value());
                    return new InvalidCredentialsException("Invalid credentials.");
                });
        PlainPassword plainPassword = PlainPassword.forLogin(command.password());
        user.validateLoginPassword(plainPassword, passwordEncoder);
        String token = tokenProvider.generateToken(user.id().value());
        long expiresIn = tokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user.id(), command.ip(), command.userAgent());
        log.info("User {} authenticated successfully.", user.username().value());
        return new LoginResponse(token, refreshToken.token().value() , expiresIn, user.username().value());
    }

}
