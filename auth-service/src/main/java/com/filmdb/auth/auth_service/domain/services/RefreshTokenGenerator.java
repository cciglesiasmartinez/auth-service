package com.filmdb.auth.auth_service.domain.services;

import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;

public interface RefreshTokenGenerator {

    RefreshTokenString generate();

}
