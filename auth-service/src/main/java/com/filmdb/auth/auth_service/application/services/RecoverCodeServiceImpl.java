package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.exception.VerificationCodeNotFoundException;
import com.filmdb.auth.auth_service.domain.model.RecoverCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;
import com.filmdb.auth.auth_service.domain.port.out.RecoverCodeGenerator;
import com.filmdb.auth.auth_service.domain.port.out.RecoverCodeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class RecoverCodeServiceImpl implements RecoverCodeService{

    private final RecoverCodeGenerator recoverCodeGenerator;
    private final RecoverCodeRepository recoverCodeRepository;

    @Override
    public RecoverCode generate(Email email, String ipAddress, String userAgent) {
        RecoverCodeString recoverCodeString = recoverCodeGenerator.generate();
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusMinutes(5);
        RecoverCode recoverCode = RecoverCode.create(recoverCodeString, email, ipAddress, userAgent,
                issuedAt, expiresAt);
        recoverCodeRepository.save(recoverCode);
        return recoverCode;
    }

    @Override
    public void validateCodeForEmail(Email email, RecoverCodeString recoverCodeString,
                                     String ipAddress, String userAgent) {
        RecoverCode recoverCode = recoverCodeRepository.findByCodeString(recoverCodeString)
                .orElseThrow(
                    () -> {
                        log.warn("Code {} not found in database.", recoverCodeString.value());
                        // TODO: Consider if this is adequate to throw a custom exception here
                        return new VerificationCodeNotFoundException();
                    });
        if (!email.value().equals(recoverCode.email().value())) {
            // TODO: Create a custom exception
            log.warn("Email mismatch between provided email {} and current stored email {}",
                    email.value(), recoverCode.email().value());
            throw new VerificationCodeNotFoundException();
        }
        recoverCodeRepository.delete(recoverCode);
    }

    @Override
    public void delete(RecoverCode recoverCode) {
        recoverCodeRepository.delete(recoverCode);
    }
}
