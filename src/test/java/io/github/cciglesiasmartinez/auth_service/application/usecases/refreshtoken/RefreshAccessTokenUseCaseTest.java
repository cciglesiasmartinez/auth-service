package io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken;

import io.github.cciglesiasmartinez.auth_service.application.context.RequestContext;
import io.github.cciglesiasmartinez.auth_service.application.dto.LoginResult;
import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenExpiredException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.LoginResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RefreshAccessTokenUseCaseTest {

    @Mock
    private RefreshTokenService refreshTokenService;

    @Mock
    private AccessTokenProvider accessTokenProvider;

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshAccessTokenUseCase useCase;

    @Test
    void shouldRefreshAccessToken() {
        // Given
        String oldTokenValue = "old-refresh-token";
        RequestContext context = new RequestContext(
                "127.0.0.1",
                "JUnit-agent",
                "en",
                oldTokenValue // refreshTokenId
        );

        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");

        RefreshToken oldRefreshToken = mock(RefreshToken.class);
        RefreshToken newRefreshToken = mock(RefreshToken.class);

        // Mock repository
        when(refreshTokenRepository.findByTokenString(RefreshTokenString.of(oldTokenValue)))
                .thenReturn(Optional.of(oldRefreshToken));
        when(oldRefreshToken.expiresAt()).thenReturn(LocalDateTime.now().plusMinutes(5));
        when(oldRefreshToken.getUserId()).thenReturn(userId);

        // Mock refresh token rotation
        RefreshTokenString newRefreshTokenString = RefreshTokenString.of("new-refresh-token");
        when(refreshTokenService.rotate(oldRefreshToken)).thenReturn(newRefreshToken);
        when(newRefreshToken.token()).thenReturn(newRefreshTokenString);

        // Mock access token generation
        when(accessTokenProvider.generateToken(userId.value())).thenReturn("fake-access-token");

        // When
        LoginResult result = useCase.execute(context);
        Envelope<LoginResponse> response = result.envelope();
        RefreshToken generatedToken = result.refreshToken();

        // Then
        assertNotNull(response.getData());
        assertEquals("fake-access-token", response.getData().getToken());
        assertEquals("new-refresh-token", generatedToken.token().value());
        assertEquals("Bearer", response.getData().getTokenType());
    }

    @Test
    void shouldThrowException_whenTokenIsNotFound() {
        // Given
        String oldTokenValue = "old-refresh-token";
        RequestContext context = new RequestContext(
                "127.0.0.1",
                "JUnit-agent",
                "en",
                oldTokenValue // refreshTokenId
        );

        when(refreshTokenRepository.findByTokenString(RefreshTokenString.of(oldTokenValue)))
                .thenReturn(Optional.empty());

        // Then/When
        assertThrows(RefreshTokenNotFoundException.class, () -> useCase.execute(context));
    }

    @Test
    void shouldThrowException_whenTokenIsExpired() {
        // Given
        String oldTokenValue = "old-refresh-token";
        RequestContext context = new RequestContext(
                "127.0.0.1",
                "JUnit-agent",
                "en",
                oldTokenValue
        );

        RefreshToken oldRefreshToken = mock(RefreshToken.class);
        when(refreshTokenRepository.findByTokenString(RefreshTokenString.of(oldTokenValue)))
                .thenReturn(Optional.of(oldRefreshToken));
        when(oldRefreshToken.expiresAt()).thenReturn(LocalDateTime.now().minusMinutes(5));

        // Then/When
        assertThrows(RefreshTokenExpiredException.class, () -> useCase.execute(context));
    }

}
