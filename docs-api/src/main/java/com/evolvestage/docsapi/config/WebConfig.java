package com.evolvestage.docsapi.config;


import com.evolvestage.docsapi.controllers.Api;
import com.evolvestage.docsapi.properties.AuthProperties;
import com.evolvestage.docsapi.properties.BasicAuthProperties;
import com.evolvestage.docsapi.security.JwtAuthenticationEntryPoint;
import com.evolvestage.docsapi.security.JwtAuthenticationTokenFilter;
import com.evolvestage.docsapi.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebConfig extends WebSecurityConfigurerAdapter {

    private static final String PRIVATE_ROLE = "PRIVATE_ROLE";
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final BasicAuthProperties basicAuthProperties;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthProperties authProperties;

    private static final List<String> EXCLUDED_PATHS = Stream.of(
            Api.ROOT + "/permanent/public/*"
    ).collect(Collectors.toList());

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Order(1)
    public JwtAuthenticationTokenFilter jwtFilter() {
        return new JwtAuthenticationTokenFilter(jwtTokenUtil, authProperties, EXCLUDED_PATHS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(basicAuthProperties.getUsername())
                .password(basicAuthProperties.getPassword())
                .roles(PRIVATE_ROLE);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] paths = EXCLUDED_PATHS.toArray(new String[]{});
        http
                .cors().and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(paths).permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

}

