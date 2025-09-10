package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Response returned after a successful password recovery flow.")
public class ResetPasswordResponse {

    @Schema(
            description = "Message containing a code confirming the operation has been successful.",
            example = "password_reset"
    )
    private String message;

    @Schema(
            description = "User email.",
            example = "joe@mail.org"
    )
    private String email;

}
