package io.github.cciglesiasmartinez.auth_service.application.usecases.getuserinfo;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.Role;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.*;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserInfoUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserInfoUseCase useCase;

    @Test
    void shouldGetUserInfo() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        GetUserInfoCommand command = new GetUserInfoCommand(userId.value());
        Username userName = Username.of("testUser");
        Email email = Email.of("test@mail.org");
        EncodedPassword encodedPassword = mock(EncodedPassword.class);
        LocalDateTime registeredAt = LocalDateTime.of(2025, 8, 26, 10, 0);
        LocalDateTime modifiedAt = LocalDateTime.of(2025, 8, 26, 10, 30);
        LocalDateTime lastLogin = LocalDateTime.of(2025, 8, 26, 10, 30);
        Set<Role> roles = new HashSet<>();
        User testUser = User.of(
                userId,
                userName,
                encodedPassword,
                email,
                registeredAt,
                modifiedAt,
                lastLogin,
                false,
                roles
        );
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // When
        Envelope<UserResponse> response = useCase.execute(command);

        // Then
        assertEquals("testUser", response.getData().getUsername());
        assertEquals("test@mail.org", response.getData().getEmail());
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        GetUserInfoCommand command = new GetUserInfoCommand(userId.value());
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

}
