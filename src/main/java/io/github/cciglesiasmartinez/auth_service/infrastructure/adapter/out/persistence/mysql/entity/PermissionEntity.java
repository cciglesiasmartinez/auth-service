package io.github.cciglesiasmartinez.auth_service.infrastructure.adapter.out.persistence.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity
@Table(name = "permissions")
public class PermissionEntity implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(length=36)
    private String id;

    @Column(nullable = false, unique = true, length = 150)
    private String name;

    @Column(nullable = true, length = 255)
    private String description;

}
