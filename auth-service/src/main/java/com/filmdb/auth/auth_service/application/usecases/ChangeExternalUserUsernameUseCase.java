package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.adapter.in.web.dto.responses.ChangeUsernameResponse;
import com.filmdb.auth.auth_service.application.commands.ChangeExternalUserUsernameCommand;
import com.filmdb.auth.auth_service.application.exception.UserIsNotExternalException;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.application.exception.UsernameAlreadyExistsException;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class ChangeExternalUserUsernameUseCase {

    private final UserRepository userRepository;

    public ChangeUsernameResponse execute(ChangeExternalUserUsernameCommand command) {
        Username newUsername = Username.of(command.newUsername());
        User user = userRepository.findById(UserId.of(command.userId()))
                .orElseThrow(() -> {
                    log.warn("User {} not found on the database.", command.userId());
                    return new UserNotFoundException();
                });
        if (!user.isExternal()) {
            throw new UserIsNotExternalException();
        }
        if (userRepository.existsByUsername(newUsername)) {
            log.warn("Username {} already exists.", newUsername.value());
            throw new UsernameAlreadyExistsException();
        }
        user.changeUsernameForExternalUser(newUsername);
        userRepository.save(user);
        log.info("Changed username to {} successfully.", user.username().value());
        return new ChangeUsernameResponse(user.username().value(), LocalDateTime.now());
    }

}
