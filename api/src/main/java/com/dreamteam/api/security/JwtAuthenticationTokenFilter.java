package com.dreamteam.api.security;

import com.dreamteam.api.properties.AuthProperties;
import com.dreamteam.api.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@CommonsLog
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private AuthProperties authProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authToken = httpServletRequest.getHeader(authProperties.getHeaderName());

        Integer userId = null;
        try {
            userId = jwtTokenUtil.getUserIdFromToken(authToken);
        } catch (IllegalArgumentException e) {
            logger.error("an error occurred during getting user_id from token", e);
        } catch (ExpiredJwtException e) {
            logger.warn("the token is expired and not valid anymore", e);
        }

        logger.info(String.format("checking authentication for user_id=%s", userId));
        if (Objects.nonNull(userId) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            JwtUser userDetails = JwtUserFactory.create(userService.findValidUserById(userId));
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UserAuthentication authentication = new UserAuthentication(userDetails);
                logger.info(String.format("authenticated user %s, setting security context", userId));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
