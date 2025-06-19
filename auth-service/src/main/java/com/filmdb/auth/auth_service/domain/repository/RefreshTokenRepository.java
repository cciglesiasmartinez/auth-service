package com.filmdb.auth.auth_service.domain.repository;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.RefreshTokenString;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByTokenString(RefreshTokenString token);
    void save(RefreshToken token);
    void delete(RefreshToken token);

}
