package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("Current password doesn't match.");
    }

}
