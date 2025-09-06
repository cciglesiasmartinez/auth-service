package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UserIsExternalException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "User is externally authenticated.";

    public UserIsExternalException() {
        super("User is externally authenticated.");
    }

    public UserIsExternalException(String message) {
        super(message);
    }

}
