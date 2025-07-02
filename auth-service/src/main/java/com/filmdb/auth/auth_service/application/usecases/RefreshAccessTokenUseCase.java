package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.RefreshAccessTokenResponse;
import com.filmdb.auth.auth_service.application.commands.RefreshAccessTokenCommand;
import com.filmdb.auth.auth_service.application.services.RefreshTokenService;
import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.repository.RefreshTokenRepository;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class RefreshAccessTokenUseCase {

    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshAccessTokenResponse execute(RefreshAccessTokenCommand command) {
        RefreshTokenString tokenString = RefreshTokenString.of(command.refreshToken());
        RefreshToken storedToken =  refreshTokenRepository.findByTokenString(tokenString)
                .orElseThrow(() -> {
                    log.warn("Token not found in database.");
                    // TODO: Create custom exception and think about how to manage it.
                    throw new RuntimeException("Token not found.");
                });
        if (storedToken.expiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Refresh token expired.");
            throw new RuntimeException("Refresh token expired.");
        }
        String subject = storedToken.getUserId().value();
        // TODO: generateToken() should ask for exp time? Then we can pass it to the response obj.
        String accessToken = tokenProvider.generateToken(subject);
        // TODO: NoArgConstructor to avoid passing tokenType?
        RefreshToken newToken = refreshTokenService.rotate(storedToken);
        log.info("New refresh token generated successfully.");
        return new RefreshAccessTokenResponse(accessToken, newToken.token().value(),
                "Bearer",3600, LocalDateTime.now());
    }

}
