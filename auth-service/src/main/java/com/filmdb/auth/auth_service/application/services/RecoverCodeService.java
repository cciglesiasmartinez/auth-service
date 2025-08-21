package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.RecoverCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.RecoverCodeString;

public interface RecoverCodeService {

    RecoverCode generate(Email email, String ipAddress, String userAgent);
    void validateCodeForEmail(Email email, RecoverCodeString recoverCodeString, String ipAddress, String userAgent);
    void delete(RecoverCode recoverCode);

}
