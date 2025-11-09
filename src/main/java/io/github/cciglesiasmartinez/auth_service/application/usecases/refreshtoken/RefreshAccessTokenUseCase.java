package io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken;

import io.github.cciglesiasmartinez.auth_service.application.context.RequestContext;
import io.github.cciglesiasmartinez.auth_service.application.dto.LoginResult;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenExpiredException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenNotFoundException;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.RefreshAccessTokenResponse;
import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
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
     * @param context the {@link RequestContext} instance containing the current refresh token.
     * @return a {@link RefreshAccessTokenResponse} instance containing the new access and refresh tokens.
     * @throws RuntimeException if token is not found.
     * @throws RuntimeException if token has expired.
     */
    public LoginResult execute(RequestContext context) {
        RefreshTokenString tokenString = RefreshTokenString.of(context.getRefreshTokenId());
        RefreshToken storedToken =  refreshTokenRepository.findByTokenString(tokenString)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Token not found in database."));
        if (storedToken.expiresAt().isBefore(LocalDateTime.now())) {
            throw new RefreshTokenExpiredException("Refresh token expired.");
        }
        String subject = storedToken.getUserId().value();
        String accessToken = accessTokenProvider.generateToken(subject);
        RefreshToken newToken = refreshTokenService.rotate(storedToken); // TODO: Add checks with context :)
        log.info("New refresh token generated successfully.");
        LoginResponse response = new LoginResponse(
                accessToken,
                accessTokenProvider.getTokenExpirationInSeconds(),
                null);
        Envelope<LoginResponse> envelope = new Envelope<>(response, new Meta()); // TODO: Clean this DTO and figure if we should delete RefreshAccessTokenDto :)
        return new LoginResult(envelope, newToken);
    }

}
