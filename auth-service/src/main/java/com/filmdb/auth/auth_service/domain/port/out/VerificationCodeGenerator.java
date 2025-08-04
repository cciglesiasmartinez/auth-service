package com.filmdb.auth.auth_service.domain.port.out;

import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;

public interface VerificationCodeGenerator {

    VerificationCodeString generate();

}
