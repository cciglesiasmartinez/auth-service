package io.github.cciglesiasmartinez.auth_service.domain.port.out;

import io.github.cciglesiasmartinez.auth_service.domain.model.UserLogin;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.ProviderKey;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;

import java.util.Optional;

public interface UserLoginRepository {

    Optional<UserLogin> findByUserId(UserId userId);
    Optional<UserLogin> findByProviderKey(ProviderKey providerKey);
    void save(UserLogin userLogin);
    void delete(UserLogin userLogin);
    boolean existsByUserId(UserId userId);
    boolean existsByProviderKey(ProviderKey providerKey);
}
