package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Token not found.";

    public RefreshTokenNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public RefreshTokenNotFoundException(String message) {
        super(message);
    }

}
