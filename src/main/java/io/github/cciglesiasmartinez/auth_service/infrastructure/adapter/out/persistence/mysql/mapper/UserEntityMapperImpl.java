package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import io.github.cciglesiasmartinez.auth_service.domain.model.*;
import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Email;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.EncodedPassword;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.UserId;
import io.github.cciglesiasmartinez.auth_service.domain.model.valueobject.Username;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    private RoleEntityMapper roleEntityMapper;

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
                entity.isExternal(),
                roleEntityMapper.toDomain(entity.getRoles())
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
        entity.setRoles(roleEntityMapper.toEntity(user.roles()));
        return entity;
    }

}