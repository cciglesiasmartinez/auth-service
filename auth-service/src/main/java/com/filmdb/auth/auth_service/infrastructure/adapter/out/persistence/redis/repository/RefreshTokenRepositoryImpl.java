package com.filmdb.auth.auth_service.infrastructure.adapter.out.persistence.redis.repository;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.port.out.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    @Qualifier("refreshTokenRedisTemplate")
    private final RedisTemplate<String, RefreshToken> redisTemplate;

    private String buildKey(String key) {
        return "refresh_token:" + key;
    }

    @Override
    public Optional<RefreshToken> findByTokenString(RefreshTokenString token) {
        String key = buildKey(token.value());
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void save(RefreshToken token) {
        String key = buildKey(token.token().value());
        Duration ttl = Duration.between(token.issuedAt(), token.expiresAt());
        redisTemplate.opsForValue().set(key, token, ttl);
        redisTemplate.expireAt(key, java.sql.Timestamp.valueOf(token.expiresAt()));
    }

    @Override
    public void delete(RefreshToken token) {
        String key = buildKey(token.token().value());
        redisTemplate.delete(key);
    }

}
