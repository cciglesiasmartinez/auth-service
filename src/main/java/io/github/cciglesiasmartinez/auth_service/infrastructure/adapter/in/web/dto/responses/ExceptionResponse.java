package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Standard DTO for exceptions.")
public class ExceptionResponse {

    @Schema(
            description = "Error code.",
            example = "invalid_password"
    )
    private String code;

    @Schema(
            description = "Error message.",
            example = "Password should contain more than 8 characters."
    )
    private String message;

    public ExceptionResponse(String code) {
        this.code = code;
    }

}
