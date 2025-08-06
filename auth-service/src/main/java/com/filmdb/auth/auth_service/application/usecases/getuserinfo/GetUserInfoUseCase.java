package com.filmdb.auth.auth_service.application.usecases.getuserinfo;

import com.filmdb.auth.auth_service.domain.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.port.out.UserRepository;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.UserResponse;
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
     * @return {@link UserResponse} object with user data.
     * @throws UserNotFoundException if user does not exist.
     */
    public UserResponse execute(GetUserInfoCommand command) {
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    log.warn("UserId {} not found in database.", command.userId());
                    throw new UserNotFoundException();
                });
        log.info("User checked their information successfully.");
        return UserResponse.fromDomainUser(user);
    }

}
