package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.requests;

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
