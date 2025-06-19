package com.filmdb.auth.auth_service.domain.repository;

import com.filmdb.auth.auth_service.domain.model.RefreshToken;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;

public interface RefreshTokenRepository {

    RefreshToken findTokenByUserId(UserId id);
    void save(RefreshToken token);
    void delete(RefreshToken token);

}
