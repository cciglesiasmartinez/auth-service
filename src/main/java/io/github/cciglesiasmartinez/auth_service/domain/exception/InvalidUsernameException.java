package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class InvalidUsernameException extends RuntimeException {

    public InvalidUsernameException(String message) {
        super(message);
    }

}
