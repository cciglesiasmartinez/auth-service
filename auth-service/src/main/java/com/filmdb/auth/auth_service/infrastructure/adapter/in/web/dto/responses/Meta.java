package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.slf4j.MDC;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard meta-data for all API responses.")
public class Meta {

    @Schema(
            description = "Request UUID.",
            example = "7f5d429e-6a7e-4499-870c-8445cb71a1e3"
    )
    String requestId;

    @Schema(
            description = "Local timestamp.",
            example = "2025-08-24T17:31:58.681726405"
    )
    LocalDateTime timestamp;

    public Meta() {
        this.requestId = MDC.get("requestId");
        this.timestamp = LocalDateTime.now();
    }

}
