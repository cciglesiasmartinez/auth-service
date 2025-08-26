package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.VerificationCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.VerificationCodeString;

import java.util.Optional;

public interface VerificationCodeRepository {

    Optional<VerificationCode> findByCodeString(VerificationCodeString verificationCodeString);
    void save(VerificationCode verificationCode);
    void delete(VerificationCode verificationCode);
}
