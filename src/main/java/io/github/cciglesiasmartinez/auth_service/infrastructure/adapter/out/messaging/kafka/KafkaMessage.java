package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.messaging.kafka;

import lombok.AllArgsConstructor;
import lombok.Getter;

// TODO: Define a better model for this class.
@AllArgsConstructor
@Getter
public class KafkaMessage {

    private final String requestId;
    private final String eventType;
    private final String message;

}
