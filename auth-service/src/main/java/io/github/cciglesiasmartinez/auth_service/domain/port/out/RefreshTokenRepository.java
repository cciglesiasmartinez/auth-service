package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshToken> findByTokenString(RefreshTokenString token);
    void save(RefreshToken token);
    void delete(RefreshToken token);

}
