package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class UserIsNotExternalException extends RuntimeException {

    public UserIsNotExternalException() {
        super("User is not an externally authenticated user.");
    }

}
