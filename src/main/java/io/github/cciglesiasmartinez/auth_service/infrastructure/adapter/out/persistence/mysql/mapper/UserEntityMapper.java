package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import io.github.cciglesiasmartinez.auth_service.domain.model.User;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.UserEntity;

//@Mapper(componentModel = "spring", uses = UserValueObjectMapper.class)
public interface UserEntityMapper {

    User toDomain(UserEntity entity);
    UserEntity toEntity(User user);

}
