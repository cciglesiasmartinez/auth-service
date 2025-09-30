package io.github.cciglesiasmartinez.auth_service.application.usecases.resetpassword;

import io.github.cciglesiasmartinez.auth_service.application.services.RecoverCodeService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ResetPasswordResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResetPasswordUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecoverCodeService recoverCodeService;

    @InjectMocks
    private ResetPasswordUseCase useCase;

    @Test
    void shouldResetPassword() {
        // Given
        ResetPasswordCommand command = new ResetPasswordCommand(
                "CODE00",
                "test@email.org",
                "Str0ngPassw0rd!",
                "127.0.0.1",
                "fakeAgent"
        );
        Email email = Email.of(command.email());
        User user = mock(User.class);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // When
        Envelope<ResetPasswordResponse> response = useCase.execute(command);

        // Then
        verify(userRepository).save(user);
        assertEquals("password_reset", response.getData().getMessage());
        assertEquals("test@email.org", response.getData().getEmail());
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        ResetPasswordCommand command = new ResetPasswordCommand(
                "CODE00",
                "test@email.org",
                "Str0ngPassw0rd!",
                "127.0.0.1",
                "fakeAgent"
        );
        Email email = Email.of(command.email());
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

}
