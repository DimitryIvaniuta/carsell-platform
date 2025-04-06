package com.carsell.platform.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequestScope
public class UserContext {

    /**
     * Returns the ID of the currently authenticated user.
     * Assumes that the authentication principal is an instance of CustomUserDetails.
     *
     * @return the user ID, or null if no authenticated user is found.
     */
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                authentication.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) authentication.getPrincipal()).getId();
        }
        return null;
    }

    /**
     * Returns the username of the currently authenticated user.
     *
     * @return the username, or null if no authenticated user is found.
     */
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * Retrieves the roles (as String values) of the currently authenticated user.
     *
     * @return a Set of role names, or an empty set if no user is authenticated.
     */
    public Set<String> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    /**
     * Checks whether the currently authenticated user has the given role.
     * The method checks for both raw role names and those prefixed with "ROLE_".
     *
     * @param role the role to check, for example "ADMIN"
     * @return true if the user has the specified role; false otherwise.
     */
    public boolean hasRole(String role) {
        Set<String> roles = getUserRoles();
        return roles.contains(role) || roles.contains("ROLE_" + role);
    }

    /**
     * Convenience method to check if the current user is an admin.
     *
     * @return true if the user has the "ADMIN" role; false otherwise.
     */
    public boolean isAdmin() {
        return hasRole("ADMIN");
    }
}

