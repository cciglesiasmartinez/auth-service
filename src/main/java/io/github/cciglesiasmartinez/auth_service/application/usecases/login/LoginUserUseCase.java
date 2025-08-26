package io.github.cciglesiasmartinez.auth_service.application.usecases.login;

import io.github.cciglesiasmartinez.auth_service.domain.exception.InvalidCredentialsException;
import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
import io.github.cciglesiasmartinez.auth_service.domain.exception.PasswordMismatchException;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
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
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * Executes the user login use case.
     *
     * @param command {@link LoginUserCommand} command containing email and password and context data (ip, userAgent).
     * @return {@link LoginResponse} object containing jwt token, expiration time and username.
     * @throws InvalidCredentialsException if the user is not found or is externally authenticated.
     * @throws PasswordMismatchException if provided password does not match stored password.
     */
    public Envelope<LoginResponse> execute(LoginUserCommand command) {
        Email email = Email.of(command.email());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn("User not found for email {}", email.value());
                    return new InvalidCredentialsException("Invalid credentials.");
                });
        if (user.isExternal()) {
            log.warn("External user {} tried to log in internally.", user.id().value());
            throw new InvalidCredentialsException("Invalid credentials.");
        }
        PlainPassword plainPassword = PlainPassword.forLogin(command.password());
        user.validateLoginPassword(plainPassword, passwordEncoder);
        user.recordLogin();
        userRepository.save(user);
        String token = accessTokenProvider.generateToken(user.id().value());
        long expiresIn = accessTokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user.id(), command.ip(), command.userAgent());
        log.info("User {} authenticated successfully.", user.username().value());
        LoginResponse response = new LoginResponse(token, refreshToken.token().value() , expiresIn, user.username().value());
        return new Envelope<LoginResponse>(response, new Meta());
    }

}
