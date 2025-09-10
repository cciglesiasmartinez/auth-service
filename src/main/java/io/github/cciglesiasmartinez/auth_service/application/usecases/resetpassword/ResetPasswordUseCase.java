package io.github.cciglesiasmartinez.auth_service.application.usecases.resetpassword;

import io.github.cciglesiasmartinez.auth_service.application.services.RecoverCodeService;
import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.PlainPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.RecoverCodeString;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.PasswordEncoder;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.ResetPasswordResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for recovering user password.
 * <p>
 * Verifies if the {@link RecoverCodeString} and {@link Email} provided in the request match the persisted values.
 * If the values match, it sets the provided {@link PlainPassword} as the new {@link User} password.
 */
@Slf4j
@Service
@AllArgsConstructor
public class ResetPasswordUseCase {

    private final UserRepository userRepository;
    private final RecoverCodeService recoverCodeService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Executes the reset password use case.
     *
     * @param command command containing recover code, email, new desired password, user's ip and agent.
     * @return A {@link ResetPasswordResponse} confirming the operation has succeeded.
     * @throws UserNotFoundException if the {@link User} is not found on the database.
     */
    public Envelope<ResetPasswordResponse> execute(ResetPasswordCommand command) {
        Email email = Email.of(command.email());
        RecoverCodeString recoverCodeString = RecoverCodeString.of(command.recoverCode());
        PlainPassword newPassword = PlainPassword.of(command.password());
        recoverCodeService.validateCodeForEmail(email, recoverCodeString, command.ip(), command.userAgent());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    String message = "User not found for email " + email.value();
                    return new UserNotFoundException(message);
                });
        user.resetPassword(newPassword, passwordEncoder);
        userRepository.save(user);
        log.info("Password reset successfully for user email {} with code {}",
                email.value(), recoverCodeString.value());
        ResetPasswordResponse data = new ResetPasswordResponse("password_reset", email.value());
        return new Envelope<>(data, new Meta());
    }

}
