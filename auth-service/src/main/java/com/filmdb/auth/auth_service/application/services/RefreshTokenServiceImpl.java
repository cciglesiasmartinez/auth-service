package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.repository.RefreshTokenRepository;
import com.filmdb.auth.auth_service.domain.services.RefreshTokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generate(UserId userId, String ipAddress, String userAgent) {
        RefreshTokenString tokenString = refreshTokenGenerator.generate();
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
