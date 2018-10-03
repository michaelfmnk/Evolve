package com.dreamteam.api.security;

import com.dreamteam.api.properties.AuthProperties;
import com.dreamteam.api.services.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@CommonsLog
@AllArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private AuthProperties authProperties;
    private final List<String> EXCLUDED_PATHS;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        if (isPathMatchesExcludePath(request.getServletPath())) {
            chain.doFilter(request, response);
        } else {
            final String authToken = request.getHeader(authProperties.getHeaderName());
            Integer userId = null;
            try {
                userId = jwtTokenUtil.getUserIdFromToken(authToken);
            } catch (IllegalArgumentException e) {
                logger.warn("an error occurred during getting user_id from token");
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore");
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
            chain.doFilter(request, response);
        }
    }

    private boolean isPathMatchesExcludePath(String servletPath) {
        List<Pattern> urlPatterns = getExcludedPatterns(EXCLUDED_PATHS);
        if (null == urlPatterns) {
            return false;
        } else {
            Iterator var3 = urlPatterns.iterator();

            Pattern pattern;
            do {
                if (!var3.hasNext()) {
                    return false;
                }

                pattern = (Pattern)var3.next();
            } while(!pattern.matcher(servletPath.toLowerCase()).matches());

            return true;
        }
    }

    private List<Pattern> getExcludedPatterns(Collection<String> excludeUrls) {
        if (null != excludeUrls && !excludeUrls.isEmpty()) {
            List<Pattern> excludedUrlPatterns = new ArrayList();
            Iterator var3 = excludeUrls.iterator();

            while(var3.hasNext()) {
                String excludeUrl = (String)var3.next();
                excludedUrlPatterns.add(Pattern.compile(excludeUrl));
            }

            return excludedUrlPatterns;
        } else {
            return Collections.emptyList();
        }
    }
}
