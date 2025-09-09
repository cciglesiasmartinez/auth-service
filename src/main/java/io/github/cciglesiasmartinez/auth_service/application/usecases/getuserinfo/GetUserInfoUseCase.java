package io.github.cciglesiasmartinez.auth_service.application.usecases.getuserinfo;

import io.github.cciglesiasmartinez.auth_service.domain.exception.UserNotFoundException;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.UserRepository;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Envelope;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.Meta;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.in.web.dto.responses.UserResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Application service for getting user info (self).
 * <p>
 * Looks for the user in the database and if exists, returns {@link UserResponse} with user data.
 */
@Slf4j
@Service
@AllArgsConstructor
public class GetUserInfoUseCase {

    private final UserRepository userRepository;

    /**
     * Executes get user info use case.
     *
     * @param command {@link GetUserInfoCommand} object containing the user id.
     * @return {@link Envelope<UserResponse>} of {@link UserResponse} type object with user data.
     * @throws UserNotFoundException if user does not exist.
     */
    public Envelope<UserResponse> execute(GetUserInfoCommand command) {
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    String message = "UserId " + command.userId() + " not found in database.";
                    return new UserNotFoundException(message);
                });
        log.info("User checked their information successfully.");
        return new Envelope<>(UserResponse.fromDomainUser(user), new Meta());
    }

}
