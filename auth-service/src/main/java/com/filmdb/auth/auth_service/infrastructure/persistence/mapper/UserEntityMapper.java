package com.filmdb.auth.auth_service.infrastructure.persistence.mapper;

import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

//@Mapper(componentModel = "spring", uses = UserValueObjectMapper.class)
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

}
