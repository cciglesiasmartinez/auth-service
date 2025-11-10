package io.github.cciglesiasmartinez.auth_service.application.usecases.logout;

import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for revoking a refresh token.
 * <p>
 * [Description here]
 */
@Slf4j
@Service
@AllArgsConstructor
public class LogoutUserUseCase {

    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenService refreshTokenService;

    public void execute(LogoutUserCommand command) {
        RefreshTokenString tokenString = RefreshTokenString.of(command.refreshToken());
        RefreshToken tokenToRevoke = refreshTokenRepository.findByTokenString(tokenString)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));
        UserId userId = UserId.of(tokenToRevoke.getUserId().value());
        refreshTokenService.delete(tokenToRevoke);
        log.info("User {} logged out", userId);
    }

}
