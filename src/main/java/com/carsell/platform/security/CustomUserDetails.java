package com.carsell.platform.security;

import lombok.Getter;
import com.carsell.platform.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

/**
 * CustomUserDetails represents a user in the Spring Security context.
 * It includes additional properties (such as user ID) and assumes
 * that all accounts are enabled, non-expired, non-locked, and credentials never expire.
 */
public class CustomUserDetails implements UserDetails {

    @Getter
    private final Long id;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;
    /**
     * Constructs a new CustomUserDetails instance based on a User domain object.
     *
     * @param user the user entity from your domain
     * @param authorities the authorities granted to the user
     */
    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authorities;
        // Map the status flags from the domain entity
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonLocked();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired.
     * <p>
     * Always returns true in this implementation.
     *
     * @return true (account is not expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * <p>
     * Always returns true in this implementation.
     *
     * @return true (account is not locked)
     */
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    /**
     * Indicates whether the user's credentials (password) have expired.
     * <p>
     * Always returns true in this implementation.
     *
     * @return true (credentials are not expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * <p>
     * Always returns true in this implementation.
     *
     * @return true (user is enabled)
     */
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
