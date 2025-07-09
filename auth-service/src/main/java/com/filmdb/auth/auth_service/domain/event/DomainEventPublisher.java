package com.filmdb.auth.auth_service.domain.event;

public interface DomainEventPublisher {

    void publish(Object event);

}
