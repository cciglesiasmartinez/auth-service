package com.filmdb.auth.auth_service.application.services;

import com.filmdb.auth.auth_service.application.commands.GetUserInfoCommand;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Application service for getting user info (self).
 * <p>
 * Looks for the user in the database and if exists, returns {@link UserResponse} with user data.
 */
@Service
@AllArgsConstructor
public class GetUserInfoService {

    private final UserRepository userRepository;

    /**
     * Executes get user info use case.
     *
     * @param command {@link GetUserInfoCommand} object containing the user id.
     * @return {@link UserResponse} object with user data.
     * @throws RuntimeException if user does not exist.
     */
    public UserResponse execute(GetUserInfoCommand command) {
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserResponse.fromDomainUser(user);
    }

}
