package com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Envelope<T> {

//    private String message;
//    private boolean success;
//    private LocalDateTime timestamp;
//    private Object data;

    // TODO: Create a factory method like .of or .success to generate the envelopes in a more pro manner

    private T data;
    private Meta meta;

}
