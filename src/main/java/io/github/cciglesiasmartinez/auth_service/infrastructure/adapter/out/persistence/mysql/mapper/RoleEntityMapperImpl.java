package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper;

import io.github.cciglesiasmartinez.auth_service.domain.model.Role;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleEntityMapperImpl implements RoleEntityMapper {

    @Override
    public Set<Role> toDomain(Set<RoleEntity> roles) {
        Set<Role> result = new HashSet<>();
        for (RoleEntity r: roles) {
            Role role = Role.create(r.getId(),r.getName(),r.getDescription());
            result.add(role);
        }
        return result;
    }

    @Override
    public Set<RoleEntity> toEntity(Set<Role> roles) {
        Set<RoleEntity> result = new HashSet<>();
        for (Role r: roles) {
            RoleEntity roleEntity = new RoleEntity(r.id(), r.name(), r.description());
            result.add(roleEntity);
        }
        return result;
    }
}
