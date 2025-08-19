package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.slf4j.MDC;

import java.time.LocalDateTime;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Meta {

    String requestId;
    LocalDateTime timestamp;

    public Meta() {
        this.requestId = MDC.get("requestId");
        this.timestamp = LocalDateTime.now();
    }

}
