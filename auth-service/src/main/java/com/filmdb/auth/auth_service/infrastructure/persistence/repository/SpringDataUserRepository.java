package com.filmdb.auth.auth_service.infrastructure.persistence.repository;

import com.filmdb.auth.auth_service.domain.model.Email;
import com.filmdb.auth.auth_service.domain.model.UserId;
import com.filmdb.auth.auth_service.domain.model.Username;
import com.filmdb.auth.auth_service.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(UserId id);
    Optional<UserEntity> findByEmail(Email email);
    Optional<UserEntity> findByUsername(Username username);
//    Optional<UserEntity> save(UserEntity user);
    void delete(UserEntity user);
    boolean existsByEmailOrUsername(String email, String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
