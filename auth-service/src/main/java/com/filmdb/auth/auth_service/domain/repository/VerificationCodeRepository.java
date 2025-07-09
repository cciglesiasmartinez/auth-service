package com.filmdb.auth.auth_service.domain.repository;

import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;

import java.util.Optional;

public interface VerificationCodeRepository {

    Optional<VerificationCode> findByCodeString(VerificationCodeString verificationCodeString);
    void save(VerificationCode verificationCode);
    void delete(VerificationCode verificationCode);
}
