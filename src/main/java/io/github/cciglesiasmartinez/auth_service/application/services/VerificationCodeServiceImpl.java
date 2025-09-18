package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.model.VerificationCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.VerificationCodeString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.VerificationCodeRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.VerificationCodeGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Service responsible for issuing register verification codes.
 * <p></p>
 * <ul>
 *     <li>Codes are bound to email, username and password.</li>
 *     <li>Each code expires after a fixed TTL.</li>
 * </ul>
 *
 * TODO: Figure out if code validation should be placed in this service instead of in the use case.
 */
@Service
@AllArgsConstructor
@Slf4j
public class VerificationCodeServiceImpl implements VerificationCodeService{

    private final VerificationCodeGenerator verificationCodeGenerator;
    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode generate(Username username, Email email, EncodedPassword password) {
        VerificationCodeString code = verificationCodeGenerator.generate();
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusMinutes(30);
        VerificationCode verificationCode = VerificationCode.create(code, username, email, password, issuedAt,
                expiresAt);
        verificationCodeRepository.save(verificationCode);
        log.info("Generated register verification code {} for username {} and email {}",
                code.value(), username.value(), email.value());
        return verificationCode;
    }

    @Override
    public void delete(VerificationCode verificationCode) {
        log.info("Deleted register verification code {}", verificationCode.verificationCodeString().value());
        verificationCodeRepository.delete(verificationCode);
    }

}
