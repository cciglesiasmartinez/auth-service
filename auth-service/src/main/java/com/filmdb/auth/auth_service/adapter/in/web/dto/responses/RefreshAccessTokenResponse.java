package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Response returned after a successful refresh token request.")
public class RefreshAccessTokenResponse {

    @Schema(
            description = "JWT access token provided.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0ODQ1OTNiZS1hYzI2LT..."
    )
    private String accessToken;

    @Schema(
            description = "New refresh token provided.",
            example = "UglbGB5x0WWNLXsrfoNrOZqldrftYRTp_Ler8LM2Z7s"
    )
    private String refreshToken;

    @Schema(
            description = "JWT access token type.",
            example = "Bearer"
    )
    private String tokenType = "Bearer";

    @Schema(
            description = "Expiration date for the token.",
            example = "2025-08-02T23:41:37.3468196"
    )
    private int expiresIn;

    @Schema(
            description = "Token issuing date.",
            example = "2025-08-01T23:41:37.3468196"
    )
    private LocalDateTime issuedAt;

}
