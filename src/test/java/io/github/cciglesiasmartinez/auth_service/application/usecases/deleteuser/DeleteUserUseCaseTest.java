package io.github.cciglesiasmartinez.auth_service.application.usecases.deleteuser;

import io.github.cciglesiasmartinez.auth_service.domain.exception.PasswordMismatchException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
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
public class DeleteUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DeleteUserUseCase useCase;

    @Test
    void shouldDeleteUser() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        DeleteUserCommand command = new DeleteUserCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!"
        );
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        useCase.execute(command);

        // Then
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        DeleteUserCommand command = new DeleteUserCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!"
        );
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenPasswordsDoNotMatch() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        PlainPassword plainPassword = PlainPassword.of("Str0ngPassw0rd!");
        DeleteUserCommand command = new DeleteUserCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!"
        );
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doThrow(new PasswordMismatchException())
                .when(user)
                .validateCurrentPassword(plainPassword, passwordEncoder);

        // When/Then
        assertThrows(PasswordMismatchException.class, () -> useCase.execute(command));
        verify(userRepository, never()).delete(any());
    }

}
