package io.github.cciglesiasmartinez.auth_service.application.usecases.register;

import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.RegisterResponse;
import io.github.cciglesiasmartinez.auth_service.application.event.VerificationEmailRequestedEvent;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserAlreadyRegisteredException;
import io.github.cciglesiasmartinez.auth_service.application.services.VerificationCodeService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.InvalidPasswordException;
import io.github.cciglesiasmartinez.auth_service.domain.model.VerificationCode;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.*;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * Application service for registering new users.
 * <p>
 * Validates that the user does not already exist (by email or username), encodes the password, creates the user,
 * and persists it using the domain repository.
 */
@Slf4j
@Service
@AllArgsConstructor
public class RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final ApplicationEventPublisher springPublisher;

    /**
     * Executes the user registration use case.
     *
     * @param command The registration command containing username, email, and raw password.
     * @return A {@link UserResponse} representing the newly registered user.
     * @throws UserAlreadyRegisteredException if a user with the same email or username already exists.
     * @throws InvalidPasswordException if the provided password does not meet security requirements.
     */
    public Envelope<RegisterResponse> execute(RegisterUserCommand command) {
        Username username = Username.of(command.username());
        Email email = Email.of(command.email());
        PlainPassword plainPassword = PlainPassword.of(command.password());
        if (userRepository.existsByEmailOrUsername(email, username)) {
            String message = "Email " + email.value() + " or username " + username.value() + " already exist.";
            throw new UserAlreadyRegisteredException(message);
        }
        EncodedPassword encodedPassword = passwordEncoder.encode(plainPassword);
        VerificationCode verificationCode = verificationCodeService.generate(username, email, encodedPassword);
        springPublisher.publishEvent(new VerificationEmailRequestedEvent(email,
                verificationCode.verificationCodeString()));
        log.info("User with username {} initiated registration process successfully", username.value());
        RegisterResponse data = new RegisterResponse("verification_sent", email.value());
        return new Envelope<>(data, new Meta());
    }

}
