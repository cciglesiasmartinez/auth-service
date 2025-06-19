package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.repository.RefreshTokenRepository;
import com.filmdb.auth.auth_service.domain.services.RefreshTokenGenerator;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenGenerator refreshTokenGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generate(User user, String ip, String userAgent) {
        RefreshTokenString tokenString = refreshTokenGenerator.generate();
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusDays(1);
        RefreshToken refreshToken = RefreshToken.create(tokenString, user.id(), issuedAt, expiresAt, ip, userAgent);
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }
}
