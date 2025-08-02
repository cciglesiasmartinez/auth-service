package com.filmdb.auth.auth_service.adapter.out.persistence.mysql.mapper;

import com.filmdb.auth.auth_service.domain.model.User;
import com.filmdb.auth.auth_service.adapter.out.persistence.mysql.entity.UserEntity;

//@Mapper(componentModel = "spring", uses = UserValueObjectMapper.class)
public interface UserEntityMapper {

    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);

}
