package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid password.";

    public InvalidPasswordException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

}
