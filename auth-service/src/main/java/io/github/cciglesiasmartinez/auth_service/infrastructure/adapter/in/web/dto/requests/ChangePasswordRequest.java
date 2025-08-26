package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "User password change request payload.")
public class ChangePasswordRequest {

    @Schema(
            description = "Current user password.",
            example = "1235abC!"
    )
    @NotBlank(message= "Current password can't be empty.")
    private String currentPassword;

    @Schema(
            description = "New desired password.",
            example = "!Cba5321"
    )
    @NotBlank(message="New password can't be empty.")
    private String newPassword;

}