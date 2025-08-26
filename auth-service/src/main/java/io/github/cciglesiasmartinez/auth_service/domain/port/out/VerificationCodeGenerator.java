package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.VerificationCodeString;

public interface VerificationCodeGenerator {

    VerificationCodeString generate();

}
