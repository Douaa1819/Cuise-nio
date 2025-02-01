package com.youcode.cuisinio.user.infrastructure.security;

import com.youcode.cuisinio.user.domain.model.agregate.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SecurityUserAdapter implements UserDetails {
    private final User user;

    public SecurityUserAdapter(User user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getEmail().value();

    }

    @Override
    public String getPassword() {
        return user.getPasswordHash().value();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


}