package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UserIsNotExternalException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "User is not an externally authenticated user.";

    public UserIsNotExternalException() {
        super(DEFAULT_MESSAGE);
    }

    public UserIsNotExternalException(String message) {
        super(message);
    }

}
