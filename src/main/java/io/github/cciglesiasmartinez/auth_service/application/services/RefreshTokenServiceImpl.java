package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Service responsible for issuing and rotating refresh tokens.
 *
 * <p>Implements the security policy for refresh token lifecycle:
 * <ul>
 *   <li>Tokens expire after a fixed TTL (time to live).</li>
 *   <li>Rotation revokes the old token immediately after issuing a new one
 *       to prevent reuse attacks.</li>
 *   <li>Issued tokens are bound to user id (uuid), IP address and user agent.</li>
 * </ul>
 */
@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generate(UserId userId, String ipAddress, String userAgent) {
        RefreshTokenString tokenString = refreshTokenGenerator.generate();
        // TODO: Use injected Clock + customizable TTL
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusDays(1);
        RefreshToken refreshToken = RefreshToken.create(tokenString, userId, issuedAt, expiresAt, ipAddress, userAgent);
        refreshTokenRepository.save(refreshToken);
        log.info("Generated new refresh token {} for user {} with ip {} and agent {}",
                tokenString.value(), userId.value(), ipAddress, userAgent);
        return refreshToken;
    }

    @Override
    public RefreshToken rotate(RefreshToken oldToken) {
        // TODO: Figure out if checks between old and new user ip and agent should be performed here?
        UserId userId = oldToken.userId();
        String ipAddress = oldToken.ipAddress();
        String userAgent = oldToken.userAgent();
        RefreshToken newToken = generate(userId, ipAddress, userAgent);
        refreshTokenRepository.delete(oldToken);
        log.info("Revoked old refresh token {} for user {} with ip {} and agent {}", oldToken.getToken().value(),
                oldToken.userId().value(), oldToken.ipAddress(), oldToken.userAgent());
        return newToken;
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
        log.info("Revoked refresh token {} for user {}", refreshToken.token(), refreshToken.userId().value());
    }
}
