package com.filmdb.auth.auth_service.application.usecases.logingoogle;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.LoginResponse;
import com.filmdb.auth.auth_service.domain.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.application.services.RefreshTokenService;
import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.port.out.UserLoginRepository;
import com.filmdb.auth.auth_service.domain.port.out.UserRepository;
import com.filmdb.auth.auth_service.domain.port.out.AccessTokenProvider;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for login users via Google OAuth2.
 * <p>
 * Receives user data from Google OAuth2 system, checks {@link ProviderKey} against our {@link UserLogin} repository
 * and if providerKey is found logs the user in and records the login date in {@code lastLogin} attribute. It
 * also provides both access and refresh token to the user in a {@link LoginResponse} instance.
 */
@Slf4j
@Service
@AllArgsConstructor
public class OAuthGoogleLoginUserUseCase {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenService refreshTokenService;

    /**
     * Execute the login user via Google OAuth2 use case.
     *
     * @param command containing provider key (google id), email, ip and user agent for the user.
     * @return a {@link LoginResponse} instance containing access and refresh tokens for the user.
     * @throws UserNotFoundException if provider key or user id are not found in our repositories.
     */
    @Transactional
    public LoginResponse execute(OAuthGoogleLoginUserCommand command) {
        ProviderKey providerKey = ProviderKey.of(command.googleId());
        Email googleEmail = Email.of(command.googleEmail());
        UserLogin userLogin = userLoginRepository.findByProviderKey(providerKey)
                .orElseThrow(() -> {
                    log.warn("User not found for providerKey {}", providerKey.value());
                    return new UserNotFoundException();
                });
        User user = userRepository.findById(userLogin.userId())
                .orElseThrow(() -> {
                    log.warn("User not found for userId {}", userLogin.userId().value());
                    return new UserNotFoundException();
                });
        user.recordLogin();
        user.updateEmailIfDifferent(googleEmail);
        userRepository.save(user);
        // Token logic
        String token = accessTokenProvider.generateToken(user.id().value());
        long expiresIn = accessTokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user.id(), command.ip(), command.userAgent());
        log.info("User logged in via Google OAuth successfully");
        return new LoginResponse(token, refreshToken.token().value() , expiresIn, user.username().value());
    }

}
