package com.dreamteam.api.services;

import com.dreamteam.api.dtos.UserDto;
import com.dreamteam.api.entities.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConverterService {

    public UserDto toDto(User entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return UserDto.builder()
                .id(entity.getUserId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .build();
    }

}
