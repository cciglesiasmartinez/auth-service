package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Refresh token expired.";

    public RefreshTokenExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public RefreshTokenExpiredException(String message) {
        super(message);
    }

}
