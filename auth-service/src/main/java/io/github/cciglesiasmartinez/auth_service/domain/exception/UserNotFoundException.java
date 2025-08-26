package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User not found.");
    }

}
