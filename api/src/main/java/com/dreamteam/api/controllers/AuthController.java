package com.dreamteam.api.controllers;

import com.dreamteam.api.dtos.AuthRequest;
import com.dreamteam.api.dtos.SignUpDto;
import com.dreamteam.api.dtos.TokenContainer;
import com.dreamteam.api.dtos.UserDto;
import com.dreamteam.api.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(Api.Auth.LOGIN)
    public TokenContainer createAuthenticationToken(@RequestBody AuthRequest request) {
        return authService.createToken(request);
    }

    @GetMapping(Api.Auth.LOGIN)
    public TokenContainer refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

    @PostMapping(Api.Auth.SIGN_UP)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto signUp(@RequestBody @Validated SignUpDto request) {
        return authService.signUp(request);
    }

}
