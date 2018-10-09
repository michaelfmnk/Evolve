package com.dreamteam.api.services;

import com.dreamteam.api.dtos.*;
import com.dreamteam.api.entities.Authority;
import com.dreamteam.api.entities.User;
import com.dreamteam.api.entities.VerificationCode;
import com.dreamteam.api.exceptions.BadRequestException;
import com.dreamteam.api.properties.AuthProperties;
import com.dreamteam.api.repositories.UsersRepository;
import com.dreamteam.api.repositories.VerificationCodeRepository;
import com.dreamteam.api.security.JwtTokenUtil;
import com.dreamteam.api.security.JwtUser;
import com.dreamteam.api.security.JwtUserFactory;
import com.dreamteam.api.utils.CodeGenerator;
import com.dreamteam.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthProperties authProperties;
    private final AuthenticationManager authenticationManager;
    private final UsersRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ConverterService converterService;
    private final MessagesService messagesService;
    private final MailjetService mailjetService;
    private final VerificationCodeRepository verificationCodeRepository;

    @Transactional
    public TokenContainer createToken(AuthRequest request) {
        User user = Objects.isNull(request.getEmail())
                ? null
                : userService.findValidUserByEmail(request.getEmail());
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final JwtUser jwtUser = JwtUserFactory.create(user);
        final String token = jwtTokenUtil.generateToken(jwtUser);
        return new TokenContainer(token, user.getUserId());
    }

    public UserDto signUp(SignUpDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException(messagesService.getMessage("user.already.exists"));
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .enabled(false)
                .lastPasswordResetDate(new Date())
                .authorities(Collections.singletonList(Authority.Type.USER.getInstance()))
                .build();

        user = userRepository.save(user);

        String code = CodeGenerator.generateCode(authProperties.getCodeLen());
        VerificationCode verificationCode
                = new VerificationCode(new VerificationCode.VerificationCodePK(user.getUserId(), code));
        verificationCodeRepository.save(verificationCode);

        mailjetService.sendEmail(
                request.getEmail(),
                messagesService.getMessage("mail.signup.subject"),
                code);
        return converterService.toDto(user);
    }

    public TokenContainer refreshToken(HttpServletRequest request) {
        String token = request.getHeader(authProperties.getHeaderName());
        String email = jwtTokenUtil.getEmailFromToken(token);
        User user = userService.findValidUserByEmail(email);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new TokenContainer(refreshedToken);
        }
        throw new BadRequestException(messagesService.getMessage("auth.token.not.refreshable"));
    }

    @Transactional
    public TokenContainer verifyCode(Integer userId, VerificationCodeDto verificationCode) {
        User user = findValidUserById(userId);
        if (user.isEnabled()) {
            throw new BadRequestException(messagesService.getMessage("user.activated.already"));
        }

        VerificationCode code = verificationCodeRepository
                .findById(VerificationCode.VerificationCodePK.builder()
                        .userId(userId)
                        .verificationCode(verificationCode.getCode())
                        .build())
                .orElseThrow(() -> new BadRequestException(messagesService.getMessage("code.not.valid")));

        user.setEnabled(true);
        userRepository.save(user);

        verificationCodeRepository.delete(code);

        final JwtUser jwtUser = JwtUserFactory.create(user);
        final String token = jwtTokenUtil.generateToken(jwtUser);
        return new TokenContainer(token);
    }

    private User findValidUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("user.not.found")));
    }
}
