package com.filmdb.auth.auth_service.infrastructure.persistence.redis.repository;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
@AllArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RedisTemplate<String, RefreshToken> redisTemplate;

    private String buildKey(String userId) {
        return "refresh_token:" + userId;
    }

    @Override
    public RefreshToken findTokenByUserId(UserId id) {
        String key = buildKey(id.value());
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void save(RefreshToken token) {
        String key = buildKey(token.userId().value());
        Duration ttl = Duration.between(token.issuedAt(), token.expiresAt());
        redisTemplate.opsForValue().set(key, token, ttl);
        redisTemplate.expireAt(key, java.sql.Timestamp.valueOf(token.expiresAt()));
    }

    @Override
    public void delete(RefreshToken token) {
        String key = buildKey(token.userId().value());
        redisTemplate.delete(key);
    }
}
