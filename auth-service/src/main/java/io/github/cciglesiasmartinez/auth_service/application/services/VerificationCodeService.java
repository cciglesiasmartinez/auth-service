package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode generate(Username username, Email email, EncodedPassword password);
    void delete(VerificationCode verificationCode);

}
