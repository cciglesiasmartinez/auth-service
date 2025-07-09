package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.UserResponse;
import com.filmdb.auth.auth_service.application.commands.VerifyUserRegistrationCommand;
import com.filmdb.auth.auth_service.application.exception.VerificationCodeNotFoundException;
import com.filmdb.auth.auth_service.domain.event.DomainEventPublisher;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.VerificationCode;
import com.filmdb.auth.auth_service.domain.model.valueobject.VerificationCodeString;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.domain.repository.VerificationCodeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class VerifyUserRegistrationUseCase {

    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final DomainEventPublisher eventPublisher;

    public UserResponse execute(VerifyUserRegistrationCommand command) {
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
        return UserResponse.fromDomainUser(user);
    }

}
