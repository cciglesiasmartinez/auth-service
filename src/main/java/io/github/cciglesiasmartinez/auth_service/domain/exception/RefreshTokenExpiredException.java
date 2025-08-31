package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class RefreshTokenExpiredException extends RuntimeException {

    public RefreshTokenExpiredException() {
        super("Refresh token expired.");
    }

}
