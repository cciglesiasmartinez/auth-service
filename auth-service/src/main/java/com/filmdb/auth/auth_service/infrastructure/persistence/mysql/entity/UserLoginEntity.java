package com.filmdb.auth.auth_service.infrastructure.persistence.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "user_logins")
public class UserLoginEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_id", length=36, nullable = false)
    private String userId;

    @Column(name="provider_name", length=50, nullable = false)
    private String providerName;

    @Column(name="provider_key", length=255, nullable = false)
    private String providerKey;

    @Column(name="provider_data", nullable = true)
    private String providerData;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    /**
     * Updates automatically the updated_at value each time we update (SQL) the entity.
     */
    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

    // TODO: Change updated_at for modified_at or the other way around in order to keep consistence between entities

}
