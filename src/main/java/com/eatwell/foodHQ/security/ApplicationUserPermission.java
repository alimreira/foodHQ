package com.eatwell.foodHQ.security;

public enum ApplicationUserPermission {
    RESERVATION_READ("reservation:read"),
    RESERVATION_WRITE("reservation:write"),
    RESERVATION_UPDATE("reservation:update"),
    RESERVATION_DELETE("reservation:delete"),
    CUSTOMER_READ("cusomer:read"),
    CUSTOMER_WRITE("customer:write"),
    CUSTOMER_UPDATE("customer:update"),
    CUSTOMER_DELETE("customer:delete");




//    The permission field holds the actual string representation of
//     the permission associated with each enum constant
    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
