package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.model.VerificationCode;

public interface VerificationCodeService {

    VerificationCode generate(Username username, Email email, EncodedPassword password);
    void delete(VerificationCode verificationCode);

}
