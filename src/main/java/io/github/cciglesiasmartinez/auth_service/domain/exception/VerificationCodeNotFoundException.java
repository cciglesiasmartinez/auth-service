package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class VerificationCodeNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid verification code.";

    public VerificationCodeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public VerificationCodeNotFoundException(String message) {
        super(message);
    }

}
