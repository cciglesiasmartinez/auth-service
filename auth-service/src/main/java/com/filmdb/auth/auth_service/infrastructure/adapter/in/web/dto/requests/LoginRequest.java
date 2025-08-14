package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Request for user login payload.")
public class LoginRequest {

    private String username;

    @Schema(
            description = "User email.",
            example = "jane@example.com"
    )
    @Email(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Plain text user password.",
            example = "12346abC!"
    )
    @NotBlank(message = "Password is required")
    private String password;

}
