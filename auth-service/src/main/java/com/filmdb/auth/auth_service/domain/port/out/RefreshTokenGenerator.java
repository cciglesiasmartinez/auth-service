package com.filmdb.auth.auth_service.domain.port.out;

import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;

public interface RefreshTokenGenerator {

    RefreshTokenString generate();

}
