package com.filmdb.auth.auth_service.domain.services;

public interface TokenProvider {

    String generateToken(String subject);
    long getTokenExpirationInSeconds();
    String getUserIdFromToken(String token);
    boolean validateJwtToken(String token);

}
