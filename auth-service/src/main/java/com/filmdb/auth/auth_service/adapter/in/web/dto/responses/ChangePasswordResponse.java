package com.filmdb.auth.auth_service.adapter.in.web.dto.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Response returned after a successful password change.")
public class ChangePasswordResponse {

    @Schema(
            description = "Message confirming the operation has been successful."
    )
    private String message;

    @Schema(
            description = "Timestamp.",
            example = "2025-08-01T23:41:37.3468196"
    )
    private LocalDateTime changedAt;

}
