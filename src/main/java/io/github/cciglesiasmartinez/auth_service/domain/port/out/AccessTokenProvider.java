package io.github.cciglesiasmartinez.auth_service.domain.port.out;

public interface AccessTokenProvider {

    String generateToken(String subject);
    long getTokenExpirationInSeconds();
    String getUserIdFromToken(String token);
    boolean validateJwtToken(String token);

}
