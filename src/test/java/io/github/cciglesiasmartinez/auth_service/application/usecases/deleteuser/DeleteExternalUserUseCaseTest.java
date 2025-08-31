package io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsNotExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteExternalUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteExternalUserUseCase useCase;

    @Test
    void shouldDeleteUser() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        DeleteExternalUserCommand command = new DeleteExternalUserCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039"
        );
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.isExternal()).thenReturn(true);

        // When
        useCase.execute(command);

        // Then
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        DeleteExternalUserCommand command = new DeleteExternalUserCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039"
        );
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserIsNotExternal() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        DeleteExternalUserCommand command = new DeleteExternalUserCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039"
        );
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.isExternal()).thenReturn(false);

        // When/Then
        assertThrows(UserIsNotExternalException.class, () -> useCase.execute(command));
    }

}
