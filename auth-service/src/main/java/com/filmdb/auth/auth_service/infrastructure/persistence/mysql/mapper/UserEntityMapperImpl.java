package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.mapper;

import com.filmdb.auth.auth_service.domain.model.*;
import com.filmdb.auth.auth_service.domain.model.valueobject.Email;
import com.filmdb.auth.auth_service.domain.model.valueobject.EncodedPassword;
import com.filmdb.auth.auth_service.domain.model.valueobject.UserId;
import com.filmdb.auth.auth_service.domain.model.valueobject.Username;
import com.filmdb.auth.auth_service.infrastructure.persistence.mysql.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public User toDomain(UserEntity entity) {
        return User.of(
                UserId.of(entity.getId()),
                Username.of(entity.getUsername()),
                EncodedPassword.of(entity.getPassword()),
                Email.of(entity.getEmail()),
                entity.getRegisteredAt(),
                entity.getModifiedAt(),
                entity.getLastLogin(),
                entity.isExternal()
        );
    }

    @Override
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.id().value());
        entity.setUsername(user.username().value());
        entity.setPassword(user.password().value());
        entity.setEmail(user.email().value());
        entity.setRegisteredAt(user.registeredAt());
        entity.setModifiedAt(user.modifiedAt());
        entity.setLastLogin(user.lastLogin());
        entity.setExternal(user.isExternal());
        return entity;
    }

}