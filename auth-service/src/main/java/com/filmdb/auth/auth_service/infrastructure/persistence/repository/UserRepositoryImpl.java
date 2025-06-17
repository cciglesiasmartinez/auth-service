package com.filmdb.auth.auth_service.infrastructure.persistence.repository;

import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.domain.model.UserId;
import com.filmdb.auth.auth_service.domain.model.Username;
import com.filmdb.auth.auth_service.domain.repository.UserRepository;
import com.filmdb.auth.auth_service.infrastructure.persistence.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SpringDataUserRepository springDataUserRepository;
    private final UserEntityMapper mapper;


    @Override
    public Optional<User> findById(UserId id) {
        return springDataUserRepository.findById(id.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return springDataUserRepository.findByEmail(email.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return springDataUserRepository.findByUsername(username.value())
                .map(mapper::toDomain);
    }

    // TODO: Consider returning the persisted User entity to allow access to generated values (e.g. ID).
    @Override
    public void save(User user) {
        springDataUserRepository.save(mapper.toEntity(user));
    }

    // TODO: Consider returning the deleted User entity.
    @Override
    public void delete(User user) {
        springDataUserRepository.delete(mapper.toEntity(user));
    }

    @Override
    public boolean existsByEmailOrUsername(Email email, Username username) {
        return springDataUserRepository.existsByEmailOrUsername(email.value(), username.value());
    }

    @Override
    public boolean existsByEmail(Email email) {
        return springDataUserRepository.existsByEmail(email.value());
    }

    @Override
    public boolean existsByUsername(Username username) {
        return springDataUserRepository.existsByUsername(username.value());
    }

}
