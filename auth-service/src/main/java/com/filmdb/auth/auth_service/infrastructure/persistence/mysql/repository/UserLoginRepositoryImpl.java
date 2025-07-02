package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.repository;

import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.repository.UserLoginRepository;
import com.filmdb.auth.auth_service.infrastructure.persistence.mysql.mapper.UserLoginEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserLoginRepositoryImpl implements UserLoginRepository {

    private final SpringDataUserLoginRepository springDataUserLoginRepository;
    private final UserLoginEntityMapper mapper;

    @Override
    public Optional<UserLogin> findByUserId(UserId userId) {
        return springDataUserLoginRepository.findByUserId(userId.value())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<UserLogin> findByProviderKey(ProviderKey providerKey) {
        return springDataUserLoginRepository.findByProviderKey(providerKey.value())
                .map(mapper::toDomain);
    }

    @Override
    public void save(UserLogin userLogin) {
        springDataUserLoginRepository.save(mapper.toEntity(userLogin));
    }

    @Override
    public void delete(UserLogin userLogin) {
        springDataUserLoginRepository.delete(mapper.toEntity(userLogin));
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return springDataUserLoginRepository.existsByUserId(userId.value());
    }

    @Override
    public boolean existsByProviderKey(ProviderKey providerKey) {
        return springDataUserLoginRepository.existsByProviderKey(providerKey.value());
    }
}
