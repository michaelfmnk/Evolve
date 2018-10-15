package com.evolvestage.docsapi.security;


import com.evolvestage.docsapi.properties.AuthProperties;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Order(1)
@Component
@CommonsLog
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;
    private AuthProperties authProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authToken = httpServletRequest.getHeader(authProperties.getHeaderName());

        Integer userId = null;
        logger.info(authToken);

        try {
            userId = jwtTokenUtil.getUserIdFromToken(authToken);
        } catch (IllegalArgumentException e) {
            logger.error("an error occurred during getting user_id from token", e);
        } catch (ExpiredJwtException e) {
            logger.warn("the token is expired and not valid anymore", e);
        }

        logger.info(String.format("checking authentication for user_id=%s", userId));
        if (Objects.nonNull(userId) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
            if (jwtTokenUtil.validateToken(authToken)) {
                UserAuthentication authentication = jwtTokenUtil.buildAuthenticationFromToken(authToken);
                logger.info(String.format("authenticated user %s, setting security context", userId));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
