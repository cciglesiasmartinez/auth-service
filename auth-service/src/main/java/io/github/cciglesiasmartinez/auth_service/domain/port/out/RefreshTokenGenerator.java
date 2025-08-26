package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;

public interface RefreshTokenGenerator {

    RefreshTokenString generate();

}
