package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.repository;

import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.infrastructure.persistence.mysql.entity.UserLoginEntity;
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
