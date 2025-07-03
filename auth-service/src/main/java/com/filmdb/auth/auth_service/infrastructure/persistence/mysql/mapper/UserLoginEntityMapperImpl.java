package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.mapper;

import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderKey;
import com.filmdb.auth.auth_service.domain.model.valueobject.ProviderName;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.infrastructure.persistence.mysql.entity.UserLoginEntity;
import org.springframework.stereotype.Component;

@Component
public class UserLoginEntityMapperImpl implements  UserLoginEntityMapper {
    @Override
    public UserLogin toDomain(UserLoginEntity userLoginEntity) {
        return UserLogin.of(
                userLoginEntity.getId(),
                UserId.of(userLoginEntity.getUserId()),
                ProviderKey.of(userLoginEntity.getProviderKey()),
                ProviderName.of(userLoginEntity.getProviderName()),
                userLoginEntity.getCreatedAt(),
                userLoginEntity.getUpdatedAt()
        );
    }

    @Override
    public UserLoginEntity toEntity(UserLogin userLogin) {
        UserLoginEntity userLoginEntity = new UserLoginEntity();
        if ( userLogin.id() != null ) {
            userLoginEntity.setId(userLogin.id());
        }
        userLoginEntity.setUserId(userLogin.userId().value());
        userLoginEntity.setProviderKey(userLogin.providerKey().value());
        userLoginEntity.setProviderName(userLogin.providerName().value());
        userLoginEntity.setCreatedAt(userLogin.createdAt());
        userLoginEntity.setUpdatedAt(userLogin.updateAt());
        return userLoginEntity;
    }
}
