package io.github.cciglesiasmartinez.auth_service.domain.model;

public class Role {

    private String id;
    private String name;
    private String description;

    private Role(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public static Role create(String id, String name, String description) {
        return new Role(id, name, description);
    }

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public String description() {
        return this.description;
    }

}
