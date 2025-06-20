package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.RefreshAccessTokenResponse;
import com.filmdb.auth.auth_service.application.commands.RefreshAccessTokenCommand;
import com.filmdb.auth.auth_service.application.services.RefreshTokenService;
import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.repository.RefreshTokenRepository;
import com.filmdb.auth.auth_service.domain.services.TokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RefreshAccessTokenUseCase {

    private final RefreshTokenService refreshTokenService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshAccessTokenResponse execute(RefreshAccessTokenCommand command) {
        RefreshTokenString tokenString = RefreshTokenString.of(command.refreshToken());
        RefreshToken storedToken =  refreshTokenRepository.findByTokenString(tokenString)
                .orElseThrow(() -> new RuntimeException("Token not found."));
        if (storedToken.expiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired.");
        }
        String subject = storedToken.getUserId().value();
        // TODO: generateToken() should ask for exp time? Then we can pass it to the response obj.
        String accessToken = tokenProvider.generateToken(subject);
        // TODO: NoArgConstructor to avoid passing tokenType?
        RefreshToken newToken = refreshTokenService.rotate(storedToken);
        return new RefreshAccessTokenResponse(accessToken, newToken.token().value(),
                "Bearer",3600, LocalDateTime.now());
    }

}
