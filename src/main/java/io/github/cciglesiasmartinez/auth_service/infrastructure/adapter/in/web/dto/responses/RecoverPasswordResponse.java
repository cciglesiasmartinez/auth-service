package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Schema(description = "Response returned after a successful recover password request.")
public class RecoverPasswordResponse {

    @Schema(
            description = "",
            example = ""
    )
    private String message;

    @Schema(
            description = "",
            example = ""
    )
    private String email;

}
