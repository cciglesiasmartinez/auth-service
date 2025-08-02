package com.filmdb.auth.auth_service.adapter.out.persistence.redis.repository;

import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;
import com.filmdb.auth.auth_service.domain.repository.VerificationCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class VerificationCodeRepositoryImpl implements VerificationCodeRepository {

    @Qualifier("verificationCodeRedisTemplate")
    private final RedisTemplate<String, VerificationCode> redisTemplate;

    private String buildKey(String key) {
        return "verification_code:" + key;
    }

    @Override
    public Optional<VerificationCode> findByCodeString(VerificationCodeString verificationCodeString) {
        String key = buildKey(verificationCodeString.value());
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    public void save(VerificationCode verificationCode) {
        String key = buildKey(verificationCode.verificationCodeString().value());
        Duration ttl = Duration.between(verificationCode.issuedAt(), verificationCode.expiresAt());
        redisTemplate.opsForValue().set(key, verificationCode, ttl);
        redisTemplate.expireAt(key, java.sql.Timestamp.valueOf(verificationCode.expiresAt()));
    }

    @Override
    public void delete(VerificationCode verificationCode) {
        String key = buildKey(verificationCode.verificationCodeString().value());
        redisTemplate.delete(key);
    }

}
