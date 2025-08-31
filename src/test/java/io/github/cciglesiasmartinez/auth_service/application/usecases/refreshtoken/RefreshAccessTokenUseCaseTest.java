package io.github.cciglesiasmartinez.auth_service.application.usecases.refreshtoken;

import io.github.cciglesiasmartinez.auth_service.application.services.RefreshTokenService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenExpiredException;
import io.github.cciglesiasmartinez.auth_service.domain.exception.RefreshTokenNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.RefreshToken;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RefreshTokenString;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.AccessTokenProvider;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RefreshTokenRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.RefreshAccessTokenResponse;
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
        RefreshAccessTokenCommand command = new RefreshAccessTokenCommand("old-refresh-token");
        RefreshTokenString oldRefreshTokenString = RefreshTokenString.of(command.refreshToken());
        UserId userId = UserId.of("6f63bf1a-d54e-452b-9d18-218a92f99039");

        RefreshToken oldRefreshToken = mock(RefreshToken.class);
        RefreshToken newRefreshToken = mock(RefreshToken.class);

        when(refreshTokenRepository.findByTokenString(oldRefreshTokenString))
                .thenReturn(Optional.of(oldRefreshToken));
        when(oldRefreshToken.expiresAt()).thenReturn(LocalDateTime.now().plusMinutes(5));
        when(oldRefreshToken.getUserId()).thenReturn(userId);

        RefreshTokenString newRefreshTokenString = RefreshTokenString.of("new-refresh-token");
        when(refreshTokenService.rotate(oldRefreshToken)).thenReturn(newRefreshToken);
        when(newRefreshToken.token()).thenReturn(newRefreshTokenString);

        when(accessTokenProvider.generateToken(userId.value())).thenReturn("fake-access-token");

        // When
        Envelope<RefreshAccessTokenResponse> response = useCase.execute(command);

        // Then
        assertNotNull(response.getData());
        assertEquals("fake-access-token", response.getData().getAccessToken());
        assertEquals("new-refresh-token", response.getData().getRefreshToken());
        assertEquals("Bearer", response.getData().getTokenType());
    }

    @Test
    void shouldThrowException_whenTokenIsNotFound() {
        // Given
        RefreshAccessTokenCommand command = new RefreshAccessTokenCommand("old-refresh-token");
        RefreshTokenString oldRefreshTokenString = RefreshTokenString.of(command.refreshToken());
        when(refreshTokenRepository.findByTokenString(oldRefreshTokenString)).thenReturn(Optional.empty());

        // Then/When
        assertThrows(RefreshTokenNotFoundException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenTokenIsExpired() {
        // Given
        RefreshAccessTokenCommand command = new RefreshAccessTokenCommand("old-refresh-token");
        RefreshTokenString oldRefreshTokenString = RefreshTokenString.of(command.refreshToken());
        RefreshToken oldRefreshToken = mock(RefreshToken.class);
        when(refreshTokenRepository.findByTokenString(oldRefreshTokenString))
                .thenReturn(Optional.of(oldRefreshToken));
        when(oldRefreshToken.expiresAt()).thenReturn(LocalDateTime.now().minusMinutes(5));

        // Then/When
        assertThrows(RefreshTokenExpiredException.class, () -> useCase.execute(command));
    }

}
