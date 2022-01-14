package com.egg.patitas.red.config;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    private final String name;
    private static final String ROLE_ = "ROLE_";

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRoleName() {
        return ROLE_ + name;
    }
}
