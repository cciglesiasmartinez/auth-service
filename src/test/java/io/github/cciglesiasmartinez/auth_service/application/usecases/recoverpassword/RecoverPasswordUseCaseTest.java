package io.github.cciglesiasmartinez.auth_service.application.usecases.recoverpassword;

import io.github.cciglesiasmartinez.auth_service.application.event.RecoverCodeRequestedEvent;
import io.github.cciglesiasmartinez.auth_service.application.services.RecoverCodeService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RecoverCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.RecoverPasswordResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecoverPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecoverCodeService recoverCodeService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private RecoverPasswordUseCase useCase;

    @Test
    void shouldRecoverPassword() {
        // Given
        RecoverPasswordCommand command = new RecoverPasswordCommand(
                "test@mail.org",
                "127.0.0.1",
                "fake-agent"
        );
        Email email = Email.of(command.email());
        RecoverCodeString recoverCodeString = RecoverCodeString.of("CODE1");
        when(userRepository.existsByEmail(email)).thenReturn(true);
        RecoverCode recoverCode = mock(RecoverCode.class);
        when(recoverCode.recoverCodeString()).thenReturn(recoverCodeString);
        when(recoverCodeService.generate(email, command.ip(), command.userAgent()))
                .thenReturn(recoverCode);

        // When
        Envelope<RecoverPasswordResponse> response = useCase.execute(command);

        // Then
        assertEquals("recover_sent", response.getData().getMessage());
        assertEquals(email.value(), response.getData().getEmail());

        ArgumentCaptor<RecoverCodeRequestedEvent> eventCaptor =
                ArgumentCaptor.forClass(RecoverCodeRequestedEvent.class);
        verify(applicationEventPublisher).publishEvent(eventCaptor.capture());

        RecoverCodeRequestedEvent published = eventCaptor.getValue();
        assertEquals(email, published.getEmail());
        assertEquals(recoverCodeString.value(), published.getCode().value());
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        RecoverPasswordCommand command = new RecoverPasswordCommand(
                "test@mail.org",
                "127.0.0.1",
                "fake-agent"
        );
        Email email = Email.of(command.email());
        when(userRepository.existsByEmail(email)).thenReturn(false);

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

}
