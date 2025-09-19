package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.exception.VerificationCodeNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RecoverCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RecoverCodeGenerator;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RecoverCodeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service responsible for issuing and validating recover codes.
 * <p>
 * <ul>
 *     <li>Codes expire after a fixed TTL (time to live).</li>
 *     <li>After validation codes are deleted.</li>
 *     <li>Each code is bound to user email, IP and user agent.</li>
 * </ul>
 */
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
        log.info("Generated recover password code {} for email {} with ip {} and agent {}",
                recoverCodeString.value(), email.value(), ipAddress, userAgent);
        return recoverCode;
    }

    @Override
    public void validateCodeForEmail(Email email, RecoverCodeString recoverCodeString,
                                     String ipAddress, String userAgent) {
        RecoverCode recoverCode = recoverCodeRepository.findByCodeString(recoverCodeString)
                .orElseThrow(
                    () -> {
                        String message = "Code " + recoverCodeString.value() + " not found in database.";
                        return new VerificationCodeNotFoundException(message);
                    });
        if (!email.value().equals(recoverCode.email().value())) {
            String message = "Email mismatch between provided email " + email.value()
                    + " and current stored email " + recoverCode.email().value();
            throw new VerificationCodeNotFoundException(message);
        }
        log.info("Validated recover password code {} for email {} with ip {} and agent {}",
                recoverCodeString.value(), email.value(), ipAddress, userAgent);
        recoverCodeRepository.delete(recoverCode);
    }

    @Override
    public void delete(RecoverCode recoverCode) {
        log.info("Deleted recover password code {}", recoverCode.recoverCodeString().value());
        recoverCodeRepository.delete(recoverCode);
    }
}
