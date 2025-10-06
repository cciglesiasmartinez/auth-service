package io.github.cciglesiasmartinez.auth_service.domain.port.out;

public interface MessageBroker {

    void sendMessage(String message);

}
