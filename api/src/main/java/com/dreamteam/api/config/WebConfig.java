package com.dreamteam.api.config;

import com.dreamteam.api.controllers.Api;
import com.dreamteam.api.properties.AuthProperties;
import com.dreamteam.api.security.JwtAuthenticationEntryPoint;
import com.dreamteam.api.security.JwtAuthenticationTokenFilter;
import com.dreamteam.api.security.JwtTokenUtil;
import com.dreamteam.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final UserDetailsService userDetailsService;
    private UserService userService;
    private JwtTokenUtil jwtTokenUtil;
    private AuthProperties authProperties;

    private static final List<String> EXCLUDED_PATHS = Stream.of(
            Api.ROOT + "/auth/*",
            Api.ROOT + "/version",
            Api.ROOT + "/users/*/verify"
    ).collect(Collectors.toList());

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtFilter() {
        return new JwtAuthenticationTokenFilter(
                userService,
                jwtTokenUtil,
                authProperties,
                EXCLUDED_PATHS
        );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
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
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/swagger-ui.html",
                        "/webjars/**"
                ).permitAll()
                .anyRequest().authenticated();
        http.addFilterAfter(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

}
