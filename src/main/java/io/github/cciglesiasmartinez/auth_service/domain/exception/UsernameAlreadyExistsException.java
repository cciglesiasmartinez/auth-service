package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Username already exists.";

    public UsernameAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public UsernameAlreadyExistsException(String message) {
        super(message);
    }

}
