package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class InvalidCredentialsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid credentials.";

    public InvalidCredentialsException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
