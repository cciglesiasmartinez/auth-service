package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.LoginUserCommand;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
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
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    private final RefreshTokenService refreshTokenService;

    /**
     * Executes the user login use case.
     *
     * @param command {@link LoginUserCommand} command containing email and password and context data (ip, userAgent).
     * @return {@link LoginResponse} object containing jwt token, expiration time and username.
     * @throws UserNotFoundException if the user is not found.
     * @throws PasswordMismatchException if provided password does not match stored password.
     */
    public LoginResponse execute(LoginUserCommand command) {
        Email email = Email.of(command.email());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());
        PlainPassword plainPassword = PlainPassword.of(command.password());
        user.validateCurrentPassword(plainPassword, passwordEncoder);
        String token = tokenProvider.generateToken(user.id().value());
        long expiresIn = tokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user, command.ip(), command.userAgent());
        System.out.println(refreshToken.toString());
        return new LoginResponse(token, refreshToken.token().value() ,expiresIn, user.username().value());
    }

}
