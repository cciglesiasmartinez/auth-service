package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;
import com.filmdb.auth.auth_service.domain.repository.VerificationCodeRepository;
import com.filmdb.auth.auth_service.domain.services.VerificationCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService{

    private final VerificationCodeGenerator verificationCodeGenerator;
    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode generate(Username username, Email email, EncodedPassword password) {
        VerificationCodeString code =verificationCodeGenerator.generate();
        LocalDateTime issuedAt = LocalDateTime.now();
        LocalDateTime expiresAt = issuedAt.plusMinutes(30);
        VerificationCode verificationCode = VerificationCode.create(code, username, email, password, issuedAt,
                expiresAt);
        verificationCodeRepository.save(verificationCode);
        return verificationCode;
    }

    @Override
    public void delete(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
    }

}
