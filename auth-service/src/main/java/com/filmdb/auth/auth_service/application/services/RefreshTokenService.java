package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.User;

public interface RefreshTokenService {

    RefreshToken generate(User user, String ip, String userAgent);

}
