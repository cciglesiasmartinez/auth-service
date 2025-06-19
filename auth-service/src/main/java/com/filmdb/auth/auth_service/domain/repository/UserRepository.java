package com.filmdb.auth.auth_service.domain.repository;

import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(UserId id);
    Optional<User> findByEmail(Email email);
    Optional<User> findByUsername(Username username);
    void save(User user);
    void delete(User user);
    boolean existsByEmailOrUsername(Email email, Username username);
    boolean existsByEmail(Email email);
    boolean existsByUsername(Username username);

}
