package com.filmdb.auth.auth_service.infrastructure.persistence.repository;

import com.filmdb.auth.auth_service.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String id);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
//    Optional<UserEntity> save(UserEntity user);
    void delete(UserEntity user);
    boolean existsByEmailOrUsername(String email, String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
