package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("Username already exists.");
    }

}
