package io.github.cciglesiasmartinez.auth_service.application.usecases.changeusername;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UsernameAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.*;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
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
public class ChangeUserUsernameUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ChangeUserUsernameUseCase useCase;

    @Test
    void shouldChangeUsername() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        PlainPassword currentPassword = PlainPassword.of("Str0ngPassw0rd!");
        Username newUsername = Username.of("newUsername");
        Username oldUsername = Username.of("oldUsername");
        ChangeUserUsernameCommand command = new ChangeUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
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
                false
        );
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(currentPassword, encodedPassword)).thenReturn(true);

        // When
        useCase.execute(command);

        // Then
        assertEquals("newUsername", user.username().value());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowException_whenUsernameAlreadyExists() {
        // Given
        ChangeUserUsernameCommand command = new ChangeUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
                "newUsername"
        );
        Username newUsername = Username.of("newUsername");
        when(userRepository.existsByUsername(newUsername)).thenReturn(true);

        // When/Then
        assertThrows(UsernameAlreadyExistsException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        ChangeUserUsernameCommand command = new ChangeUserUsernameCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
                "newUsername"
        );
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

}
