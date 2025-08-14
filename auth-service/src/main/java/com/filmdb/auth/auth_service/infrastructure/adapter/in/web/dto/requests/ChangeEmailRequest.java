package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "User email change request payload.")
public class ChangeEmailRequest {

    @Schema(
            description = "Current user password.",
            example = "12345abC!"
    )
    @NotBlank(message="Current password can't be empty.")
    private String currentPassword;

    @Schema(
            description = "New email desired.",
            example = "jane@newmail.com"
    )
    @Email
    @NotBlank(message="Email can't be empty.")
    private String email;

}
