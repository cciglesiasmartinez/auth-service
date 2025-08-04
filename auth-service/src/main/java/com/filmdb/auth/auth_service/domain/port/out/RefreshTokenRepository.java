package com.filmdb.auth.auth_service.domain.port.out;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByTokenString(RefreshTokenString token);
    void save(RefreshToken token);
    void delete(RefreshToken token);

}
