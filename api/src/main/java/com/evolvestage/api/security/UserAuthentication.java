package com.evolvestage.api.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private final JwtUser jwtUser;
    private boolean authenticated = true;

    public UserAuthentication(JwtUser user) {
        this.jwtUser = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return jwtUser.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return jwtUser.getId();
    }

    @Override
    public Object getDetails() {
        return jwtUser;
    }

    @Override
    public Object getPrincipal() {
        return jwtUser.getId();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return jwtUser.getUsername();
    }

    public Integer getId() {
        return jwtUser.getId();
    }
}
