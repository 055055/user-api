package com.api.common.type;

import lombok.Getter;

@Getter
public enum AccountRole implements EnumInterface{

    USER("01","user"),
    ADMIN("02","admin");

    private String code;
    private String name;

    AccountRole(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
