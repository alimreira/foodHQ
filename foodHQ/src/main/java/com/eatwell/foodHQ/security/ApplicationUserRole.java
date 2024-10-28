package com.eatwell.foodHQ.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole {
    CUSTOMER(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.CUSTOMER_WRITE,ApplicationUserPermission.CUSTOMER_UPDATE,ApplicationUserPermission.CUSTOMER_DELETE,
            ApplicationUserPermission.RESERVATION_WRITE,ApplicationUserPermission.RESERVATION_UPDATE,ApplicationUserPermission.RESERVATION_DELETE)),
    ADMINTRAINEE(Sets.newHashSet(ApplicationUserPermission.RESERVATION_READ,ApplicationUserPermission.CUSTOMER_READ,
            ApplicationUserPermission.CUSTOMER_UPDATE,ApplicationUserPermission.RESERVATION_UPDATE));

    private final Set<ApplicationUserPermission> permissionSet;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities (){
         Set<SimpleGrantedAuthority> permissions = getPermissionSet().stream()
                 .map((permission)-> new SimpleGrantedAuthority(permission.getPermission()))
                 .collect(Collectors.toSet());
          permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
          return permissions;
    };

    public Set<ApplicationUserPermission> getPermissionSet() {
        return permissionSet;
    }

    ApplicationUserRole(Set<ApplicationUserPermission> permissionSet) {
        this.permissionSet = permissionSet;
    }
}
