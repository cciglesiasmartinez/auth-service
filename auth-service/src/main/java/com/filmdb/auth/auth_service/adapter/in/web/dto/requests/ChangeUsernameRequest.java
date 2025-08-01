package com.filmdb.auth.auth_service.adapter.in.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Username change user request payload.")
public class ChangeUsernameRequest {

    @Schema(
            description = "Current user password.",
            example = "1235abC!"
    )
    @NotBlank(message="Current password can't be empty.")
    private String currentPassword;

    @Schema(
            description = "New desired username.",
            example = "enaj1990"
    )
    @NotBlank(message="Username can't be empty.")
    private String username;

}
