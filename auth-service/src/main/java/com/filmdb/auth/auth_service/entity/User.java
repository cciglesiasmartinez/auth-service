package com.filmdb.auth.auth_service.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

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
    private LocalDateTime registeredAt = LocalDateTime.now();

    @Column(name="modified_at")
    private LocalDateTime modifiedAt;

    /**
     * Updates automatically the modified_at value each time we update (SQL) the entity.
     */
    @PreUpdate
    public void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }

}