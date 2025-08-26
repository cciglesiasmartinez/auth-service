package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@AllArgsConstructor
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
        return refreshToken;
    }

    @Override
    public RefreshToken rotate(RefreshToken oldToken) {
        UserId userId = oldToken.userId();
        String ipAddress = oldToken.ipAddress();
        String userAgent = oldToken.userAgent();
        RefreshToken newToken = generate(userId, ipAddress, userAgent);
        refreshTokenRepository.delete(oldToken);
        return newToken;
    }
}
