package io.github.cciglesiasmartinez.auth_service.application.usecases.verifyregistration;

import io.github.cciglesiasmartinez.auth_service.domain.event.DomainEventPublisher;
import io.github.cciglesiasmartinez.auth_service.domain.exception.VerificationCodeNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.VerificationCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.VerificationCodeString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.VerificationCodeRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VerifyUserRegistrationUseCaseTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @Mock
    private DomainEventPublisher eventPublisher;

    @InjectMocks
    private VerifyUserRegistrationUseCase useCase;

    @Test
    void shouldVerifyUserRegistration() {
        // Given
        VerifyUserRegistrationCommand command = new VerifyUserRegistrationCommand("CODE00");
        VerificationCodeString verificationCodeString = VerificationCodeString.of(command.verificationCode());
        VerificationCode verificationCode = mock(VerificationCode.class);
        when(verificationCodeRepository.findByCodeString(verificationCodeString))
                .thenReturn(Optional.of(verificationCode));

        when(verificationCode.username()).thenReturn(Username.of("fakeUser"));
        when(verificationCode.email()).thenReturn(Email.of("fake@mail.com"));
        when(verificationCode.password()).thenReturn(EncodedPassword.of(("fakePassword")));

        // When
        Envelope<UserResponse> response = useCase.execute(command);

        // Then
        assertEquals("fake@mail.com", response.getData().getEmail());
        assertEquals("fakeUser", response.getData().getUsername());
    }

    @Test
    void shouldThrowException_whenVerificationIsNotFound() {
        // Given
        VerifyUserRegistrationCommand command = new VerifyUserRegistrationCommand("CODE00");
        VerificationCodeString verificationCodeString = VerificationCodeString.of(command.verificationCode());
        when(verificationCodeRepository.findByCodeString(verificationCodeString))
                .thenReturn(Optional.empty());

        // When/Then
        assertThrows(VerificationCodeNotFoundException.class, () -> useCase.execute(command));
    }

}
