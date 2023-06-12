package com.cocarius.security.role;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cocarius.security.role.Permission.*;

public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_WRITE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    MANAGER_READ,
                    MANAGER_WRITE,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_WRITE,
                    MANAGER_UPDATE,
                    MANAGER_DELETE
            )
    ),
    USER(Collections.emptySet());

    private final Set<Permission> permissions;
    private static final Role[] arrValues = values();

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getGetPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        Set<SimpleGrantedAuthority> authorities = getGetPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }

    public static Role forName(String name){
        for (Role e: arrValues) {
            if (e.name().equals(name)) return e;
        }
        return null;
    }
}
