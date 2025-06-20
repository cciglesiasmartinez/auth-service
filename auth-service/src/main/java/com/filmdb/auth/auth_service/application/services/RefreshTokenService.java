package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;

public interface RefreshTokenService {

    RefreshToken generate(UserId userId, String ipAddress, String userAgent);
    RefreshToken rotate(RefreshToken refreshToken);

}
