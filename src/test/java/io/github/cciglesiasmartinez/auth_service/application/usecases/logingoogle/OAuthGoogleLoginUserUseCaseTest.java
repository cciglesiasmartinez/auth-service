package io.github.cciglesiasmartinez.auth_service.application.usecases.logingoogle;

import io.github.cciglesiasmartinez.auth_service.application.dto.LoginResult;
import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.UserLogin;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.*;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserLoginRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
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
public class OAuthGoogleLoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLoginRepository userLoginRepository;

    @Mock
    private AccessTokenProvider accessTokenProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private OAuthGoogleLoginUserUseCase useCase;

    @Test
    void shouldLoginUser() {
        // Given
        OAuthGoogleLoginUserCommand command = new OAuthGoogleLoginUserCommand(
                "googleId-number",
                "mail@google.com",
                "127.0.0.1",
                "fake-agent"
        );
        ProviderKey providerKey = ProviderKey.of("googleId-number");
        Email googleMail = Email.of("mail@google.com");
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");
        LocalDateTime createdAt = LocalDateTime.of(2025, 8, 26, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2025, 8, 26, 10, 30);
        UserLogin userLogin = UserLogin.of(
                123L,
                userId,
                providerKey,
                ProviderName.GOOGLE,
                createdAt,
                updatedAt
        );
        when(userLoginRepository.findByProviderKey(providerKey)).thenReturn(Optional.of(userLogin));
        User user = mock(User.class);
        when(user.id()).thenReturn(userId);
        Username username = Username.of("google123");
        when(user.username()).thenReturn(username);
        when(userRepository.findById(userLogin.userId())).thenReturn(Optional.of(user));
        when(accessTokenProvider.generateToken(user.id().value())).thenReturn("access-token");
        RefreshTokenString refreshTokenString = RefreshTokenString.of("refresh-token");
        RefreshToken refreshToken = RefreshToken.create(
                refreshTokenString,
                userId,
                createdAt,
                updatedAt,
                command.ip(),
                command.userAgent()
        );
        when(refreshTokenService.generate(user.id(), command.ip(), command.userAgent()))
                .thenReturn(refreshToken);

        // When
        LoginResult result = useCase.execute(command);
        Envelope<LoginResponse> response = result.envelope();

        // Then
        assertEquals(user.username().value(), response.getData().getUsername());
        assertEquals("access-token", response.getData().getToken());
//        assertEquals("refresh-token", response.getData().getRefreshToken());
        verify(user).recordLogin();
        verify(user).updateEmailIfDifferent(googleMail);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowException_whenUserIsNotFound() {
        // Given
        OAuthGoogleLoginUserCommand command = new OAuthGoogleLoginUserCommand(
                "googleId-number",
                "mail@google.com",
                "127.0.0.1",
                "fake-agent"
        );
        ProviderKey providerKey = ProviderKey.of("googleId-number");
        when(userLoginRepository.findByProviderKey(providerKey)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(UserNotFoundException.class, () -> useCase.execute(command));
    }

}
