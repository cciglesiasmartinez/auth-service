package com.filmdb.auth.auth_service.domain.repository;

import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.Username;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(Email email);
    Optional<User> findByUsername(Username username);
    void save(User user);
    void delete(User user);
    boolean existsByEmailOrUsername(Email email, Username username);
    boolean existsByEmail(Email email);
    boolean existsByUsername(Username username);

}
