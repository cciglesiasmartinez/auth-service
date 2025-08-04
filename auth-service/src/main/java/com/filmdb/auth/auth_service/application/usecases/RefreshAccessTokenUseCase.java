package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.RefreshAccessTokenResponse;
import com.filmdb.auth.auth_service.application.commands.RefreshAccessTokenCommand;
import com.filmdb.auth.auth_service.application.services.RefreshTokenService;
import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.port.out.RefreshTokenRepository;
import com.filmdb.auth.auth_service.domain.port.out.AccessTokenProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Application service for issuing a new refresh and access token.
 * <p>
 * Verifies if current refresh token is valid. In that case, creates an access token, creates a new
 * {@link RefreshToken} refresh token and deletes the provided (current) {@link RefreshToken}.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RefreshAccessTokenUseCase {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Executes the refresh and access token use case.
     *
     * @param command containing the current refresh token.
     * @return a {@link RefreshAccessTokenResponse} instance containing the new access and refresh tokens.
     * @throws RuntimeException if token is not found.
     * @throws RuntimeException if token has expired.
     */
    public RefreshAccessTokenResponse execute(RefreshAccessTokenCommand command) {
        RefreshTokenString tokenString = RefreshTokenString.of(command.refreshToken());
        RefreshToken storedToken =  refreshTokenRepository.findByTokenString(tokenString)
                .orElseThrow(() -> {
                    log.warn("Token not found in database.");
                    // TODO: Create custom exception and think about how to manage it.
                    return new RuntimeException("Token not found.");
                });
        if (storedToken.expiresAt().isBefore(LocalDateTime.now())) {
            log.warn("Refresh token expired.");
            throw new RuntimeException("Refresh token expired.");
        }
        String subject = storedToken.getUserId().value();
        // TODO: generateToken() should ask for exp time? Then we can pass it to the response obj.
        String accessToken = accessTokenProvider.generateToken(subject);
        // TODO: NoArgConstructor to avoid passing tokenType?
        RefreshToken newToken = refreshTokenService.rotate(storedToken);
        log.info("New refresh token generated successfully.");
        return new RefreshAccessTokenResponse(accessToken, newToken.token().value(),
                "Bearer",3600, LocalDateTime.now());
    }

}
