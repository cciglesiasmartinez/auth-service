package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Response returned after a successful login.")
public class LoginResponse {

    @Schema(
            description = "JWT access token.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0ODQ1OTNiZS1hYzI2LT..."
    )
    private String token;

    @Schema(
            description = "JWT token type.",
            example = "Bearer"
    )
    private String tokenType = "Bearer";

    @Schema(
            description = "Refresh token.",
            example = "UglbGB5x0WWNLXsrfoNrOZqldrftYRTp_Ler8LM2Z7s"
    )
    private String refreshToken;

    @Schema(
            description = "Token expiration date.",
            example = "2025-08-02T23:41:37.3468196"
    )
    private long expiresIn;

    @Schema(
            description = "User username.",
            example = "jane1990"
    )
    private String username;

    public LoginResponse(String token, String refreshToken, long expiresIn, String username) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.username = username;
    }

}
