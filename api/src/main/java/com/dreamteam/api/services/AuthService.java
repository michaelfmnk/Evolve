package com.dreamteam.api.services;

import com.dreamteam.api.dtos.AuthRequest;
import com.dreamteam.api.dtos.SignUpDto;
import com.dreamteam.api.dtos.TokenContainer;
import com.dreamteam.api.dtos.UserDto;
import com.dreamteam.api.entities.User;
import com.dreamteam.api.exceptions.BadRequestException;
import com.dreamteam.api.properties.AuthProperties;
import com.dreamteam.api.repositories.UsersRepository;
import com.dreamteam.api.security.JwtTokenUtil;
import com.dreamteam.api.security.JwtUser;
import com.dreamteam.api.security.JwtUserFactory;
import com.dreamteam.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
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
                .enabled(true)
                .lastPasswordResetDate(new Date())
                .build();

        user = userRepository.save(user);
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
}
