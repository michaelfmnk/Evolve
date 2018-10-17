package com.evolvestage.api.services;

import com.evolvestage.api.dtos.*;
import com.evolvestage.api.entities.Authority;
import com.evolvestage.api.entities.User;
import com.evolvestage.api.entities.VerificationCode;
import com.evolvestage.api.exceptions.BadRequestException;
import com.evolvestage.api.properties.AuthProperties;
import com.evolvestage.api.repositories.UsersRepository;
import com.evolvestage.api.repositories.VerificationCodeRepository;
import com.evolvestage.api.security.JwtTokenUtil;
import com.evolvestage.api.security.JwtUser;
import com.evolvestage.api.security.JwtUserFactory;
import com.evolvestage.api.utils.CodeGenerator;
import com.evolvestage.api.utils.MessagesService;
import com.evolvestage.api.utils.TimeProvider;
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
import java.util.Objects;
import java.util.Optional;

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
    private final TimeProvider timeProvider;

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
        Optional<User> foundUser = userRepository.findUserByEmail(request.getEmail());

        if (foundUser.isPresent() && foundUser.get().isEnabled()) {
            throw new BadRequestException(messagesService.getMessage("user.already.exists"));
        }

        User user = foundUser.map(found -> {
            found.setPassword(passwordEncoder.encode(request.getPassword()));
            found.setFirstName(request.getFirstName());
            found.setLastName(request.getLastName());
            found.setLastPasswordResetDate(timeProvider.getDate());
            return found;
        })
                .orElseGet(() -> User.builder()
                        .email(request.getEmail())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .enabled(false)
                        .lastPasswordResetDate(timeProvider.getDate())
                        .authorities(Collections.singletonList(Authority.Type.USER.getInstance()))
                        .build());

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
