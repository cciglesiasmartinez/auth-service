package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import io.github.cciglesiasmartinez.auth_service.domain.model.Role;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.RoleEntity;

import java.util.Set;

public interface RoleEntityMapper {

    Set<Role> toDomain(Set<RoleEntity> roles);
    Set<RoleEntity> toEntity(Set<Role> roles);

}
