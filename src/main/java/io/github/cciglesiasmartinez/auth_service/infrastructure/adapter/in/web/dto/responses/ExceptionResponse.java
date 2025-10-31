package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard DTO for exceptions.")
public class ExceptionResponse {

    @Schema(
            description = "Error URI.",
            example = "https://api.example.com/errors/user_already_registered"
    )
    private String type;

    @Schema(
            description = "HTTP status code.",
            example = "409"
    )
    private int status;

    @Schema(
            description = "Error description.",
            example = "User is already registered."
    )
    private String title;

    @Schema(
            description = "Error details (if provided).",
            example = "User can't be found in our database due to reasons."
    )
    private String detail;

    @Schema(
            description = "Error code.",
            example = "user_already_registered"
    )
    private String code;

}
