package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.Role;

import java.util.Set;

public interface AccessTokenProvider {

    String generateToken(String subject);
    String generateToken(String subject, Set<Role> roles);
    long getTokenExpirationInSeconds();
    String getUserIdFromToken(String token);
    boolean validateJwtToken(String token);

}
