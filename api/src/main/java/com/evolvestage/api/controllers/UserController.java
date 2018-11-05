package com.evolvestage.api.controllers;

import com.evolvestage.api.dtos.TokenContainer;
import com.evolvestage.api.dtos.VerificationCodeDto;
import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.services.AuthService;
import com.evolvestage.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Objects;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class UserController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping(Api.Users.VERIFY)
    public TokenContainer verifyCode(@PathVariable("user_id") Integer userId,
                                     @RequestBody VerificationCodeDto verificationCode) {
        return authService.verifyCode(userId, verificationCode);
    }

    @GetMapping(Api.Users.USER)
    public ResponseEntity<?> getUserInfo(@PathVariable("user_id") Integer userId, @ApiIgnore UserAuthentication auth) {
        if (Objects.equals(userId, auth.getId())) {
            return ResponseEntity.ok(userService.getUserInfo(auth.getId()));
        }
        return ResponseEntity.ok(userService.getUserBriefInfo(userId));
    }

}
