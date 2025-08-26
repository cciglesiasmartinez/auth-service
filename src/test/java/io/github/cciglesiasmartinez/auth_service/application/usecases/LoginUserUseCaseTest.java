package io.github.cciglesiasmartinez.auth_service.application.usecases;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.*;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
import io.github.cciglesiasmartinez.auth_service.application.usecases.login.LoginUserCommand;
import io.github.cciglesiasmartinez.auth_service.application.usecases.login.LoginUserUseCase;
import io.github.cciglesiasmartinez.auth_service.domain.exception.InvalidCredentialsException;
import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.*;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
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
public class LoginUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AccessTokenProvider accessTokenProvider;
    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private LoginUserUseCase useCase;

    @Test
    void shouldThrowException_whenEmailIsNotFound() {
        // Given
        Email email = Email.of("test@mail.com");
        PlainPassword plainPassword = PlainPassword.of("Str0ngPassword!");
        String ip = "localhost";
        String userAgent = "mockAgent";
        LoginUserCommand command = new LoginUserCommand(email.value(), plainPassword.value(), ip, userAgent);
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenUserUsesExternalAuthentication() {
        // Given
        Email email = Email.of("test@mail.com");
        PlainPassword plainPassword = PlainPassword.of("Str0ngPassword!");
        String ip = "localhost";
        String userAgent = "mockAgent";
        LoginUserCommand command = new LoginUserCommand(email.value(), plainPassword.value(), ip, userAgent);
        User user = mock(User.class);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserId userId = mock(UserId.class);
        when(userId.value()).thenReturn("mock-id");
        when(user.id()).thenReturn(userId);
        when(user.isExternal()).thenReturn(true);

        // When/Then
        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenPasswordIsWrong() {
        // Given
        Email email = Email.of("test@mail.com");
        PlainPassword plainPassword = PlainPassword.of("Str0ngPassw0rd!");
        String ip = "localhost";
        String userAgent = "mockAgent";
        LoginUserCommand command = new LoginUserCommand(email.value(), plainPassword.value(), ip, userAgent);
        User user = mock(User.class);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(user.isExternal()).thenReturn(false);

        doThrow(new InvalidCredentialsException("Invalid credentials."))
                .when(user).validateLoginPassword(any(PlainPassword.class), any(PasswordEncoder.class));

        // When/Then
        assertThrows(InvalidCredentialsException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldLoginUser() {
        // Given
        Email email = Email.of("test@mail.com");
        PlainPassword plainPassword = PlainPassword.of("Str0ngPassword!");
        String ip = "localhost";
        String userAgent = "mockAgent";
        LoginUserCommand command = new LoginUserCommand(email.value(), plainPassword.value(), ip, userAgent);
        User user = mock(User.class);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        UserId userId = mock(UserId.class);
        when(user.id()).thenReturn(userId);
        when(userId.value()).thenReturn("mock-user-id");
        when(user.isExternal()).thenReturn(false);
        doNothing().when(user).recordLogin();

        doNothing().when(user).validateLoginPassword(any(PlainPassword.class), eq(passwordEncoder));

        when(accessTokenProvider.generateToken("mock-user-id")).thenReturn("mock-token");
        when(accessTokenProvider.getTokenExpirationInSeconds()).thenReturn(3600L);

        Username username = Username.of("testUser");
        when(user.username()).thenReturn(username);
        RefreshToken refreshToken = mock(RefreshToken.class);
        when(refreshToken.token()).thenReturn(RefreshTokenString.of("mock-refresh-token"));
        when(refreshTokenService.generate(userId, ip, userAgent)).thenReturn(refreshToken);

        // When
        Envelope<LoginResponse> response = useCase.execute(command);

        // Then
//        assertEquals("mock-token", response.getToken());
//        assertEquals("mock-refresh-token", response.getRefreshToken());
//        assertEquals(3600L, response.getExpiresIn());
//        assertEquals("testUser", response.getUsername());

        verify(user).recordLogin();
        verify(userRepository).save(user);
    }

}
