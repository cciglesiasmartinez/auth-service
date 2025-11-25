package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@ToString // TODO: Delete in production
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(length=36)
    private String id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(length=60, nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(name="registered_at", nullable = false)
    private LocalDateTime registeredAt;

    @Column(name="modified_at")
    private LocalDateTime modifiedAt;

    @Column(name="last_login")
    private LocalDateTime lastLogin;

    @Column(name="is_external")
    private boolean isExternal;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();

//    /**
//     * Updates automatically the modified_at value each time we update (SQL) the entity.
//     */
//    @PreUpdate
//    public void onUpdate() {
//        this.modifiedAt = LocalDateTime.now();
//    }

}
