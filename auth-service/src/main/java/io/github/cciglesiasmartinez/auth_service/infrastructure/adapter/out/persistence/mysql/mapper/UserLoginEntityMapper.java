package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import io.github.cciglesiasmartinez.auth_service.domain.model.UserLogin;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.UserLoginEntity;

public interface UserLoginEntityMapper {

    UserLogin toDomain(UserLoginEntity userLoginEntity);
    UserLoginEntity toEntity(UserLogin userLogin);

}
