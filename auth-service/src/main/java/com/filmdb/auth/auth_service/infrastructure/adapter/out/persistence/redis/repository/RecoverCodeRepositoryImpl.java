package com.filmdb.auth.auth_service.infrastructure.adapter.out.persistence.redis.repository;

import com.filmdb.auth.auth_service.domain.model.RecoverCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;
import com.filmdb.auth.auth_service.domain.port.out.RecoverCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RecoverCodeRepositoryImpl implements RecoverCodeRepository {

    @Qualifier("recoverCodeRedisTemplate")
    private final RedisTemplate<String, RecoverCode> redisTemplate;

    private String buildKey(String key) {
        return "recover_code:" + key;
    }

    @Override
    public Optional<RecoverCode> findByCodeString(RecoverCodeString recoverCodeString) {
        String key = buildKey(recoverCodeString.value());
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void save(RecoverCode recoverCode) {
        String key = buildKey(recoverCode.recoverCodeString().value());
        Duration ttl = Duration.between(recoverCode.issuedAt(), recoverCode.expiresAt());
        redisTemplate.opsForValue().set(key, recoverCode, ttl);
        redisTemplate.expireAt(key, Timestamp.valueOf(recoverCode.expiresAt()));
    }

    @Override
    public void delete(RecoverCode recoverCode) {
        String key = buildKey(recoverCode.recoverCodeString().value());
        redisTemplate.delete(key);
    }
}
