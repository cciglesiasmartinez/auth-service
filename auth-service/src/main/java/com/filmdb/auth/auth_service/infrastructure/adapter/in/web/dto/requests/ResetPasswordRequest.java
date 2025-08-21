package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Response returned after a successful recover password request.")
public class ResetPasswordRequest {

    @Schema(
            description = "",
            example = ""
    )
    @NotBlank
    private String recoverCode;

    @Schema(
            description = "",
            example = ""
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
            description = "",
            example = ""
    )
    @NotBlank
    private String password;

}
