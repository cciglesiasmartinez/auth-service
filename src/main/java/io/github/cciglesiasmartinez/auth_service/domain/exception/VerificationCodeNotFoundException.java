package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class VerificationCodeNotFoundException extends RuntimeException {

    public VerificationCodeNotFoundException() {
        super("Invalid verification code.");
    }

}
