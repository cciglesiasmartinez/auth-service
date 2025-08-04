package com.filmdb.auth.auth_service.domain.port.out;

import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;

import java.util.Optional;

public interface UserLoginRepository {

    Optional<UserLogin> findByUserId(UserId userId);
    Optional<UserLogin> findByProviderKey(ProviderKey providerKey);
    void save(UserLogin userLogin);
    void delete(UserLogin userLogin);
    boolean existsByUserId(UserId userId);
    boolean existsByProviderKey(ProviderKey providerKey);
}
