package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(length=36)
    private String id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(nullable = true, length = 255)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<Permission> permissions = new HashSet<>();

}
