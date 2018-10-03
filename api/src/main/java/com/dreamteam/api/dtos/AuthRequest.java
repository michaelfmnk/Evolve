package com.dreamteam.api.dtos;

import com.dreamteam.api.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Email
    private String email;
    @ValidPassword
    private String password;
}
