package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@ToString // TODO: Delete in production.
@NoArgsConstructor
//@AllArgsConstructor
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

//    @ManyToMany(fetch = FetchType.EAGER) // TODO: Solve this so we can avoid using EAGER
//    @JoinTable(name = "role_permissions",
//            joinColumns = @JoinColumn(name = "role_id"),
//            inverseJoinColumns = @JoinColumn(name = "permission_id"))
//    private Set<PermissionEntity> permissions = new HashSet<>();

    // Temporary 3-arg constructor. TODO: Delete later.
    public RoleEntity(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
