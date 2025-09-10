package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests;

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
@Schema(description = "Request for reset password flow (end).")
public class ResetPasswordRequest {

    @Schema(
            description = "Recover code.",
            example = "BC1PO3"
    )
    @NotBlank
    private String recoverCode;

    @Schema(
            description = "User email.",
            example = "jane@mail.org"
    )
    @Email
    @NotBlank
    private String email;

    @Schema(
            description = "New desired password.",
            example = "Str0ngPassw0rd!"
    )
    @NotBlank
    private String password;

}
