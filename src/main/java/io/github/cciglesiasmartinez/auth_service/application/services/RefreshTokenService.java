package io.github.cciglesiasmartinez.auth_service.application.services;

import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;

public interface RefreshTokenService {

    RefreshToken generate(UserId userId, String ipAddress, String userAgent);
    RefreshToken rotate(RefreshToken refreshToken);
    void delete(RefreshToken refreshToken);

}
