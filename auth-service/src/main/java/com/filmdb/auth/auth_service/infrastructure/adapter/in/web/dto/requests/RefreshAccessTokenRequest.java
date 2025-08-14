package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "User refresh (rotate) token request payload.")
public class RefreshAccessTokenRequest {

    @Schema(
            description = "Actual refresh token.",
            example = "UglbGB5x0WWNLXsrfoNrOZqldrftYRTp_Ler8LM2Z7s"
    )
    @NotBlank(message = "Refresh token cannot be empty.")
    private String refreshToken;

}
