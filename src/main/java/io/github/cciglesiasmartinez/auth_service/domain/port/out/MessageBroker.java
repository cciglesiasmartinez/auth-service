package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.messaging.kafka.KafkaMessage;

public interface MessageBroker {

    void sendMessage(String message);
    void sendMessage(KafkaMessage kafkaMessage);
}
