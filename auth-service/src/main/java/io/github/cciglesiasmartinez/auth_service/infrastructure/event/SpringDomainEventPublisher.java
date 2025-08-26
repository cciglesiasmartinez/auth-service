package io.github.cciglesiasmartinez.auth_service.infrastructure.event;

import io.github.cciglesiasmartinez.auth_service.domain.event.DomainEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher springPublisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    @Override
    public void publish(Object event) {
        springPublisher.publishEvent(event);
    }

}
