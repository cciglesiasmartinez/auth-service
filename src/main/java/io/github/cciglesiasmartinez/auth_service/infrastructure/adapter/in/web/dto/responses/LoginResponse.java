package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
            description = "Token expiration term,",
            example = "600"
    )
    private long expiresIn;

    @Schema(
            description = "User username.",
            example = "jane1990"
    )
    private String username;

    public LoginResponse(String token, long expiresIn, String username) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.username = username;
    }

}
