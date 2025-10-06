package io.github.cciglesiasmartinez.auth_service.application.usecases.registergoogle;

import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.EmailAlreadyExistsException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.Role;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RoleRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserLoginRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
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
public class OAuthGoogleRegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLoginRepository userLoginRepository;

    @Mock
    private AccessTokenProvider accessTokenProvider;

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private OAuthGoogleRegisterUserUseCase useCase;

    @Test
    void shouldRegisterUser() {
        // Given
        OAuthGoogleRegisterUserCommand command = new OAuthGoogleRegisterUserCommand(
                "1fake-google-id",
                "fake@gmail.com",
                "127.0.0.1",
                "fakeAgent"
        );
        Email googleEmail = Email.of(command.googleEmail());

        when(userRepository.existsByEmail(googleEmail)).thenReturn(false);

        Role userRole = mock(Role.class);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));

        RefreshToken mockRefreshToken = mock(RefreshToken.class);
        RefreshTokenString mockRefreshTokenString = RefreshTokenString.of("refresh-token");
        when(mockRefreshToken.token()).thenReturn(mockRefreshTokenString);

        when(refreshTokenService.generate(any(), eq(command.ip()), eq(command.userAgent())))
                .thenReturn(mockRefreshToken);

        when(accessTokenProvider.generateToken(any())).thenReturn("fake-access-token");
        when(accessTokenProvider.getTokenExpirationInSeconds()).thenReturn(3600L);

        // When
        Envelope<LoginResponse> response = useCase.execute(command);

        // Then
        assertEquals("fake-access-token", response.getData().getToken());
        assertEquals("refresh-token", response.getData().getRefreshToken());
    }

    @Test
    void shouldThrowException_whenGoogleEmailIsAlreadyRegistered() {
        // Given
        OAuthGoogleRegisterUserCommand command = new OAuthGoogleRegisterUserCommand(
                "1fake-google-id",
                "fake@gmail.com",
                "127.0.0.1",
                "fakeAgent"
        );
        Email googleEmail = Email.of(command.googleEmail());
        when(userRepository.existsByEmail(googleEmail)).thenReturn(true);

        // When/Then
        assertThrows(EmailAlreadyExistsException.class, () -> useCase.execute(command));
    }

}
