package com.filmdb.auth.auth_service.application.usecases;

import com.filmdb.auth.auth_service.application.commands.DeleteExternalUserCommand;
import com.filmdb.auth.auth_service.application.exception.UserIsNotExternalException;
import com.filmdb.auth.auth_service.application.exception.UserNotFoundException;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DeleteExternalUserUseCase {

    private final UserRepository userRepository;

    public void execute(DeleteExternalUserCommand command) {
        UserId userId = UserId.of(command.userId());
        User user = userRepository.findById(userId).
                orElseThrow(() -> {
                    log.warn("UserId {} not found in database", command.userId());
                    return new UserNotFoundException();
                });
        if (!user.isExternal()) {
            throw new UserIsNotExternalException();
        }
        userRepository.delete(user);
        log.info("Deleted their user successfully.");
    }

}
