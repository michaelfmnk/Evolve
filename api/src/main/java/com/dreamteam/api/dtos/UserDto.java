package com.dreamteam.api.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UserDto implements Serializable {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
}
