package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.mapper;

import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.infrastructure.persistence.mysql.entity.UserLoginEntity;

public interface UserLoginEntityMapper {

    UserLogin toDomain(UserLoginEntity userLoginEntity);
    UserLoginEntity toEntity(UserLogin userLogin);

}
