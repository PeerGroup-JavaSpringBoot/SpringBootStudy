package dev.aquashdw.community.auth.model;

import dev.aquashdw.community.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Collections;

public class AutoLockUserDetails implements UserDetails {
    private final String username;
    private final String password;
    private final Instant lastLogIn;
    private static final long ACCOUNT_LOCK_TRIGGER_TIME = 60 * 60 * 24 * 30;

    public AutoLockUserDetails(UserEntity userEntity){
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.lastLogIn = userEntity.getLastLogin();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return lastLogIn.isBefore(Instant.now().plusSeconds(ACCOUNT_LOCK_TRIGGER_TIME));
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
