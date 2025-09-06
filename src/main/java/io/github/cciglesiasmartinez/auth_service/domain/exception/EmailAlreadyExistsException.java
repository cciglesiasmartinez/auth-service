package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Email already exists.";

    public EmailAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
