package io.github.cciglesiasmartinez.auth_service.domain.event;

public interface DomainEventPublisher {

    void publish(Object event);

}
