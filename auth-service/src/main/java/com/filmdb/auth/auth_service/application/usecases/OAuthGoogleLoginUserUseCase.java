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
import com.filmdb.auth.auth_service.domain.repository.UserLoginRepository;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import jakarta.transaction.Transactional;
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
        String token = tokenProvider.generateToken(user.id().value());
        long expiresIn = tokenProvider.getTokenExpirationInSeconds();
        RefreshToken refreshToken = refreshTokenService.generate(user.id(), command.ip(), command.userAgent());
        log.info("User logged in via Google OAuth successfully");
        return new LoginResponse(token, refreshToken.token().value() , expiresIn, user.username().value());
    }

}
