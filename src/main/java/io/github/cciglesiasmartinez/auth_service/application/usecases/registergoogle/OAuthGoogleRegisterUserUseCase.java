package io.github.cciglesiasmartinez.auth_service.application.usecases.registergoogle;

import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.domain.exception.EmailAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.UserLogin;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.ProviderKey;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.ProviderName;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserLoginRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for registering users via Google OAuth2
 * <p>
 * Checks if Google {@link Email} exists in repository. If yes, throws an exception. If not, proceeds creating a
 * {@link User} instance using Google provided data and saves it into our {@link UserRepository}. Then, generates
 * both access and refresh tokens and delivers them.
 */
@Slf4j
@Service
@AllArgsConstructor
public class OAuthGoogleRegisterUserUseCase {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * Executes the user registration via Google OAuth2 use case.
     *
     * @param command Registration command containing email and provider key (user id).
     * @return {@link LoginResponse} instance containing access and refresh tokens, expiration time and username.
     * @throws EmailAlreadyExistsException if email is already registered in our database.
     */
    @Transactional
    public Envelope<LoginResponse> execute(OAuthGoogleRegisterUserCommand command) {
        ProviderKey providerKey = ProviderKey.of(command.googleId());
        ProviderName providerName = ProviderName.GOOGLE;
        Email googleEmail = Email.of(command.googleEmail());
        if (userRepository.existsByEmail(googleEmail)) {
//            log.warn("Email {} already exists.", googleEmail.value());
            String message = "Email " + googleEmail.value() + " already exists.";
            throw new EmailAlreadyExistsException(message);
        }
        User user = User.createExternalUser(googleEmail, providerKey, providerName);
        userRepository.save(user);
        UserLogin userLogin = UserLogin.create(user.id(), providerKey, providerName);
        userLoginRepository.save(userLogin);
        // Token logic
        String token = accessTokenProvider.generateToken(user.id().value());
        long expiresIn = accessTokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user.id(), command.ip(), command.userAgent());
        log.info("User registered via Google OAuth successfully");
        LoginResponse data = new LoginResponse(token, refreshToken.token().value(), expiresIn, user.username().value());
        return new Envelope<>(data, new Meta());
    }

}
