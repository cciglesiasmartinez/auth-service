package io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsNotExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UsernameAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeExternalUserUsernameUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChangeExternalUserUsernameUseCase useCase;

    @Test
    void shouldChangeExternalUserUsername() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        Username newUsername = Username.of("newUsername");
        Username oldUsername = Username.of("oldUsername");
        ChangeExternalUserUsernameCommand command = new ChangeExternalUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "newUsername"
        );
        EncodedPassword encodedPassword = mock(EncodedPassword.class);
        Email email = Email.of("test@mail.org");
        when(userRepository.existsByUsername(newUsername)).thenReturn(false);
        LocalDateTime registeredAt = LocalDateTime.of(2025, 8, 26, 10, 0);
        LocalDateTime modifiedAt = LocalDateTime.of(2025, 8, 26, 10, 30);
        LocalDateTime lastLogin = LocalDateTime.of(2025, 8, 26, 10, 30);
        User user = User.of(
                userId,
                oldUsername,
                encodedPassword,
                email,
                registeredAt,
                modifiedAt,
                lastLogin,
                true
        );
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        useCase.execute(command);

        // Then
        assertEquals("newUsername", user.username().value());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowException_whenUsernameAlreadyExists() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        ChangeExternalUserUsernameCommand command = new ChangeExternalUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "newUsername"
        );
        Username newUsername = Username.of("newUsername");
        when(userRepository.existsByUsername(newUsername)).thenReturn(true);
        User user = mock(User.class);
        when(user.isExternal()).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When/Then
        assertThrows(UsernameAlreadyExistsException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        ChangeExternalUserUsernameCommand command = new ChangeExternalUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "newUsername"
        );
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserIsNotExternal() {
        // Given
        ChangeExternalUserUsernameCommand command = new ChangeExternalUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "newUsername"
        );
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(user.isExternal()).thenReturn(false);

        // When/Then
        assertThrows(UserIsNotExternalException.class, () -> useCase.execute(command));
    }

}
