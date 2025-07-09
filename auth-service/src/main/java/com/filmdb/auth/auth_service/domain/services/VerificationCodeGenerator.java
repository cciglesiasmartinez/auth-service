package com.filmdb.auth.auth_service.domain.services;

import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;

public interface VerificationCodeGenerator {

    VerificationCodeString generate();

}
