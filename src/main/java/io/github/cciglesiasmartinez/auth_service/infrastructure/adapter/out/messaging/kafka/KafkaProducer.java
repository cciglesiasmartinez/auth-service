package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.messaging.kafka;

import io.github.cciglesiasmartinez.auth_service.domain.port.out.MessageBroker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaProducer implements MessageBroker {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, KafkaMessage> kafkaMessageKafkaTemplate;

    @Override
    public void sendMessage(String message) {
        String topicName = "user.events";
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message {} with offset {}.", message, result.getRecordMetadata().offset());
            } else {
                String exceptionMessage = "Unable to send message " + message + " due to " + ex.getMessage();
                log.warn("Unable to send message {} due to {}.", message, ex.getMessage());
                throw new RuntimeException(exceptionMessage);
            }
        });
    }

    public void sendMessage(KafkaMessage kafkaMessage) {
//        MDC.put("requestId", kafkaMessage.getRequestId());
        String topicName = "user.events";
        CompletableFuture<SendResult<String, KafkaMessage>> future = kafkaMessageKafkaTemplate.send(topicName, kafkaMessage);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                MDC.put("requestId", kafkaMessage.getRequestId());
                log.info("Sent message {} with offset {}.", kafkaMessage.getMessage(), result.getRecordMetadata().offset());
                MDC.clear();
            } else {
                MDC.put("requestId", kafkaMessage.getRequestId());
                String exceptionMessage = "Unable to send message " + kafkaMessage.getMessage() + " due to " + ex.getMessage();
                log.warn("Unable to send message {} due to {}.", kafkaMessage.getMessage(), ex.getMessage());
                MDC.clear();
                throw new RuntimeException(exceptionMessage);
            }
        });
    }
}
