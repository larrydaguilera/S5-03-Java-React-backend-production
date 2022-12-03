package com.ncs503.Babybook.auth.utility;

public enum RoleEnum {
    USER, ADMIN, GUEST;

    private static final String PREFIX = "SCOPE_";

    public String getFullRoleName(){
        return PREFIX + name();
    }
    public String getSimpleRoleName(){
        return name();
    }
}
