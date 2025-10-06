package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.repository;

import io.github.cciglesiasmartinez.auth_service.domain.model.Role;
import io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.mapper.RoleEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import io.github.cciglesiasmartinez.auth_service.domain.port.out.RoleRepository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private SpringDataRoleRepository springDataRoleRepository;
    private RoleEntityMapper mapper;

    @Override
    public Optional<Role> findByName(String name) {
        return springDataRoleRepository.findByName(name)
                .map(mapper::toDomain);
    }

}
