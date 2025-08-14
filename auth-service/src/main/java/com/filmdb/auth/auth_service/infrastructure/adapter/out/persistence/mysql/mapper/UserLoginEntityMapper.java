package com.filmdb.auth.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import com.filmdb.auth.auth_service.domain.model.UserLogin;
import com.filmdb.auth.auth_service.infrastructure.adapter.out.persistence.mysql.entity.UserLoginEntity;

public interface UserLoginEntityMapper {

    UserLogin toDomain(UserLoginEntity userLoginEntity);
    UserLoginEntity toEntity(UserLogin userLogin);

}
