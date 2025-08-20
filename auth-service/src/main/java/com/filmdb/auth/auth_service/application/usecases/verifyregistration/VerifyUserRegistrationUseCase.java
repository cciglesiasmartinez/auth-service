package com.filmdb.auth.auth_service.application.usecases.verifyregistration;

import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import com.filmdb.auth.auth_service.infrastructure.adapter.in.web.dto.responses.UserResponse;
import com.filmdb.auth.auth_service.domain.exception.VerificationCodeNotFoundException;
import com.filmdb.auth.auth_service.domain.event.DomainEventPublisher;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;
import com.filmdb.auth.auth_service.domain.port.out.UserRepository;
import com.filmdb.auth.auth_service.domain.port.out.VerificationCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Application service for verifying a user registry code.
 *  <p>
 *  Looks for a {@link VerificationCodeString} in our {@link VerificationCodeRepository}. If it exists, creates a new
 *  {@link User} instance with the data contained in the {@link VerificationCode} instance stored and persists it in our
 *  database and publishes the resulting domain event. Finally, deletes the {@link VerificationCode} instance.
 */
@Slf4j
@Service
@AllArgsConstructor
public class VerifyUserRegistrationUseCase {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final DomainEventPublisher eventPublisher;

    /**
     * Executes the verify registration use case.
     *
     * @param command {@link VerifyUserRegistrationCommand} containing the verification code.
     * @return {@link UserResponse} containing the newly registered user data.
     * @throws VerificationCodeNotFoundException if the verification code is not found.
     */
    public Envelope<UserResponse> execute(VerifyUserRegistrationCommand command) {
        // TODO: Change the response DTO for this use case
        VerificationCodeString verificationCodeString = VerificationCodeString.of(command.verificationCode());
        VerificationCode verificationCode = verificationCodeRepository.findByCodeString(verificationCodeString)
                .orElseThrow(() -> {
                    log.warn("Failed to retrieved verification code {}.", verificationCodeString.value());
                    return new VerificationCodeNotFoundException();
                });
        User user = User.register(verificationCode.username(), verificationCode.email(), verificationCode.password());
        user.pullEvents().forEach(eventPublisher::publish);
        userRepository.save(user);
        verificationCodeRepository.delete(verificationCode);
        log.info("User {} verified their registration email successfully.", user.id().value());
        UserResponse data = UserResponse.fromDomainUser(user);
        return new Envelope<>(data, new Meta());
    }

}
