package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.repository;

import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);
    boolean existsByEmailOrUsername(String email, String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

}
