package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UserAlreadyRegisteredException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Username or email already registered.";

    public UserAlreadyRegisteredException() {
        super("Username or email already registered.");
    }

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }

}
