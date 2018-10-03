package com.dreamteam.api.security;


import com.dreamteam.api.entities.User;

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
                .build();
    }

}
