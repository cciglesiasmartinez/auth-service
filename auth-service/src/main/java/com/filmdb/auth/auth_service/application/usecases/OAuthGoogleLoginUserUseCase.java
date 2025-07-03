package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.LoginResponse;
import com.filmdb.auth.auth_service.application.commands.OAuthGoogleLoginUserCommand;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.application.services.RefreshTokenService;
import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderName;
import com.filmdb.auth.auth_service.domain.repository.UserLoginRepository;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OAuthGoogleLoginUserUseCase {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    public LoginResponse execute(OAuthGoogleLoginUserCommand command) {
        // TODO: Add email validation. If mail from Google changes, then change in our db.
        ProviderKey providerKey = ProviderKey.of(command.googleId());
        ProviderName providerName = ProviderName.GOOGLE;
        Email googleEmail = Email.of(command.googleEmail());
        UserLogin userLogin = userLoginRepository.findByProviderKey(providerKey)
                .orElseThrow(() -> {
                    log.warn("User not found for providerKey", providerKey.value());
                    throw new UserNotFoundException();
                });
        User user = userRepository.findById(userLogin.userId())
                .orElseThrow(() -> {
                    log.warn("User not found for userId", userLogin.userId().value());
                    throw new UserNotFoundException();
                });
        // Token logic
        String token = tokenProvider.generateToken(user.id().value());
        long expiresIn = tokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user.id(), command.ip(), command.userAgent());
        log.info("User logged in via Google OAuth successfully");
        return new LoginResponse(token, refreshToken.token().value() , expiresIn, user.username().value());
    }

}
