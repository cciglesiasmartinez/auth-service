package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.model.RecoverCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;

public interface RecoverCodeService {

    RecoverCode generate(Email email, String ipAddress, String userAgent);
    void validateCodeForEmail(Email email, RecoverCodeString recoverCodeString, String ipAddress, String userAgent);
    void delete(RecoverCode recoverCode);

}
