package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.RegisterResponse;
import com.filmdb.auth.auth_service.application.commands.RegisterUserCommand;
import com.filmdb.auth.auth_service.application.event.VerificationEmailRequestedEvent;
import com.filmdb.auth.auth_service.application.exception.UserAlreadyRegisteredException;
import com.filmdb.auth.auth_service.application.services.VerificationCodeService;
import com.filmdb.auth.auth_service.domain.exception.InvalidPasswordException;
import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.PlainPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterUserUseCaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private VerificationCodeService verificationCodeService;
    @Mock
    private ApplicationEventPublisher springPublisher;

    @InjectMocks
    private RegisterUserUseCase useCase;

    @Test
    void shouldThrowException_whenUserAlreadyExists() {
        // Given
        Email email = Email.of("test@mail.com");
        Username username = Username.of("testUsername");
        PlainPassword password = PlainPassword.of("Str0ngPassword!");
        RegisterUserCommand command = new RegisterUserCommand(username.value(), email.value(), password.value());
        when(userRepository.existsByEmailOrUsername(email, username)).thenReturn(true);

        // When/Then
        assertThrows(UserAlreadyRegisteredException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowException_whenPasswordIsTooWeak() {
        // Given
        Email email = Email.of("test@mail.com");
        Username username = Username.of("testUsername");
        String password = "weak";
        RegisterUserCommand command = new RegisterUserCommand(username.value(), email.value(), password);

        // When/Then
        assertThrows(InvalidPasswordException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldRegisterUser() {
        // Given
        Email email = Email.of("test@mail.com");
        Username username = Username.of("testUsername");
        PlainPassword password = PlainPassword.of("Str0ngPassword!");
        RegisterUserCommand command = new RegisterUserCommand(username.value(), email.value(), password.value());
        VerificationCode verificationCode = mock(VerificationCode.class);
        EncodedPassword encodedPassword = mock(EncodedPassword.class);

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(userRepository.existsByEmailOrUsername(email,username)).thenReturn(false);
        when(verificationCodeService.generate(username, email, encodedPassword)).thenReturn(verificationCode);

        // When
        RegisterResponse response = useCase.execute(command);

        // Then
        assertEquals("Verification code sent to email.", response.getMessage());
        assertEquals(email.value(), response.getEmail());
        verify(springPublisher).publishEvent(any(VerificationEmailRequestedEvent.class));
    }

}
