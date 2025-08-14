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
@Schema(description = "Request for user registration payload.")
public class RegisterRequest {

    @Schema(
            description = "Chosen username.",
            example = "janedoe1990"
    )
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(
            description = "User email.",
            example = "jane@example.com"
    )
    @Email
    @NotBlank(message = "Invalid email format")
    private String email;

    @Schema(
            description = "Plain text password.",
            example = "12345abC!"
    )
    @NotBlank(message = "Password is required")
    private String password;

}
