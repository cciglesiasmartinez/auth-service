package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.RegisterResponse;
import com.filmdb.auth.auth_service.application.commands.RegisterUserCommand;
import com.filmdb.auth.auth_service.application.event.VerificationEmailRequestedEvent;
import com.filmdb.auth.auth_service.application.exception.UserAlreadyRegisteredException;
import com.filmdb.auth.auth_service.application.services.VerificationCodeService;
import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.*;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.services.PasswordEncoder;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.UserResponse;
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
     */
    public RegisterResponse execute(RegisterUserCommand command) {
        Username username = Username.of(command.username());
        Email email = Email.of(command.email());
        PlainPassword plainPassword = PlainPassword.of(command.password());
        if (userRepository.existsByEmailOrUsername(email, username)) {
            log.warn("Email {} or username {} already exist.", email.value(), username.value());
            throw new UserAlreadyRegisteredException();
        }
        EncodedPassword encodedPassword = passwordEncoder.encode(plainPassword);
        VerificationCode verificationCode = verificationCodeService.generate(username, email, encodedPassword);
        springPublisher.publishEvent(new VerificationEmailRequestedEvent(email,
                verificationCode.verificationCodeString()));
        log.info("User with username {} initiated registration process successfully", username.value());
        return new RegisterResponse("Verification code sent to email.", email.value());
    }

}
