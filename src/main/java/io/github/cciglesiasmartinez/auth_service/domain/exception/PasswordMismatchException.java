package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class PasswordMismatchException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Current password does not match.";

    public PasswordMismatchException() {
        super(DEFAULT_MESSAGE);
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

}
