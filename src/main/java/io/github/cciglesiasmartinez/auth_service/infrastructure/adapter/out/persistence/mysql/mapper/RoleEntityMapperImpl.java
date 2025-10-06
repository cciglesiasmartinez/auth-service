package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import io.github.cciglesiasmartinez.auth_service.domain.model.Role;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleEntityMapperImpl implements RoleEntityMapper {

    @Override
    public Set<Role> toDomain(Set<RoleEntity> roles) {
        return roles.stream()
                .map(this::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<RoleEntity> toEntity(Set<Role> roles) {
        return roles.stream()
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }

    @Override
    public RoleEntity toEntity(Role role) {
        return new RoleEntity(role.id(), role.name(), role.description());
    }

    @Override
    public Role toDomain(RoleEntity entity) {
        return Role.create(entity.getId(), entity.getName(), entity.getDescription());
    }
}
