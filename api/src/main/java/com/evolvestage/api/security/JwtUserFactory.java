package com.evolvestage.api.security;


import com.evolvestage.api.entities.Authority;
import com.evolvestage.api.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

public class JwtUserFactory {
    private JwtUserFactory(){}

    public static JwtUser create(User user){
        return JwtUser.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .password(user.getPassword())
                .enabled(user.isEnabled())
                .lastPasswordResetDate(user.getLastPasswordResetDate())
                .authorities(mapToGrantedAuthorities(user.getAuthorities()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return emptyIfNull(authorities)
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName().name()))
                .collect(Collectors.toList());
    }
}
