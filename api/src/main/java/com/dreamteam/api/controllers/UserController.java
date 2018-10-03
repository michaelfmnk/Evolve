package com.dreamteam.api.controllers;

import com.dreamteam.api.dtos.TokenContainer;
import com.dreamteam.api.dtos.VerificationCodeDto;
import com.dreamteam.api.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class UserController {

    private final AuthService authService;

    @PostMapping(Api.Users.VERIFY)
    public TokenContainer verifyCode(@PathVariable("user_id") Integer userId,
                                     @RequestBody VerificationCodeDto verificationCode) {
        return authService.verifyCode(userId, verificationCode);
    }

}
