package com.filmdb.auth.auth_service.infrastructure.security.oauth;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

@Service
public class GoogleTokenVerifier {

    private final JwtDecoder googleJwtDecoder;

    public GoogleTokenVerifier(JwtDecoder googleJwtDecoder) {
        this.googleJwtDecoder = googleJwtDecoder;
    }

    public GoogleUser extractUserInfo(String idToken) {
        Jwt jwt = googleJwtDecoder.decode(idToken);
        return new GoogleUser(
                jwt.getSubject(),
                jwt.getClaim("email"),
                jwt.getClaim("name"),
                jwt.getClaim("picture")
        );
    }

    public record GoogleUser(String googleId, String email, String name, String picture) {}
}
