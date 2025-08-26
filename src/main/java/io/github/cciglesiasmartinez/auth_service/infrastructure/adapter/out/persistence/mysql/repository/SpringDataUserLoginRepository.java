package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.repository;

import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.UserLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataUserLoginRepository  extends JpaRepository<UserLoginEntity, Long> {

    Optional<UserLoginEntity> findByUserId(String userId);
    Optional<UserLoginEntity> findByProviderKey(String providerKey);
    boolean existsByUserId(String userId);
    boolean existsByProviderKey(String providerKey);

}
