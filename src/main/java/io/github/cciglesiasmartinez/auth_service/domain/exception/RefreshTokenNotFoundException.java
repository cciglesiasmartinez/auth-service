package io.github.cciglesiasmartinez.auth_service.domain.exception;

public class RefreshTokenNotFoundException extends RuntimeException {

    public RefreshTokenNotFoundException() {
        super("Token not found");
    }

}
