package io.github.cciglesiasmartinez.auth_service.application.usecases.changeemail;

import io.github.cciglesiasmartinez.auth_service.domain.exception.EmailAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserIsExternalException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChangeUserEmailUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ChangeUserEmailUseCase useCase;

    @Test
    void shouldChangeUserEmail() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        PlainPassword currentPassword = PlainPassword.of("Str0ngPassw0rd!");
        Email newEmail = Email.of("new@mail.org");
        ChangeUserEmailCommand command = new ChangeUserEmailCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
                "new@mail.org"
        );
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);
        User user = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        useCase.execute(command);

        // Then
        verify(user).changeEmail(currentPassword, newEmail, passwordEncoder);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowException_whenNewEmailIsAlreadyRegistered() {
        // Given
        Email newEmail = Email.of("new@mail.org");
        ChangeUserEmailCommand command = new ChangeUserEmailCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
                "new@mail.org"
        );
        when(userRepository.existsByEmail(newEmail)).thenReturn(true);

        // When/Then
        assertThrows(EmailAlreadyExistsException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        ChangeUserEmailCommand command = new ChangeUserEmailCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
                "new@mail.org"
        );
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserIsExternallyAuthenticated() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        Email newEmail = Email.of("new@mail.org");
        ChangeUserEmailCommand command = new ChangeUserEmailCommand(
                "6f63bf1a-d54e-452b-9d18-218a92f99039",
                "Str0ngPassw0rd!",
                "new@mail.org"
        );
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);
        Username username = Username.of("testUser");
        EncodedPassword encodedPassword = mock(EncodedPassword.class);
        Email oldEmail = Email.of("old@mail.org");
        LocalDateTime registeredAt = LocalDateTime.of(2025, 8, 26, 10, 0);
        LocalDateTime modifiedAt = LocalDateTime.of(2025, 8, 26, 10, 30);
        LocalDateTime lastLogin = LocalDateTime.of(2025, 8, 26, 10, 30);
        User externalUser = User.of(
                userId,
                username,
                encodedPassword,
                oldEmail,
                registeredAt,
                modifiedAt,
                lastLogin,
                true
        );
        when(userRepository.findById(userId)).thenReturn(Optional.of(externalUser));

        // When / Then
        assertThrows(UserIsExternalException.class, () -> useCase.execute(command));
        verify(userRepository, never()).save(any());
    }

}
