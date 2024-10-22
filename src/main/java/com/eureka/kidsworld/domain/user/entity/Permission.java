package com.eureka.kidsworld.domain.user.entity;

import lombok.Getter;

@Getter
public enum Permission {
    READ("READ_AUTHORITY"),
    CREATE("CREATE_AUTHORITY"),
    UPDATE("UPDATE_AUTHORITY"),
    DELETE("DELETE_AUTHORITY");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
