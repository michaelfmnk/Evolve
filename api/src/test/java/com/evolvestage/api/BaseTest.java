package com.evolvestage.api;

import com.evolvestage.api.properties.AuthProperties;
import com.evolvestage.api.security.JwtTokenUtil;
import com.evolvestage.api.security.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailjet.client.MailjetClient;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@SqlGroup({@Sql("classpath:test-clean.sql"), @Sql})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    protected Headers headers;
    protected Headers badHeaders;
    @LocalServerPort
    protected Integer port;
    @SpyBean
    protected JwtTokenUtil jwtTokenUtil;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected AuthProperties authProperties;
    @MockBean
    protected MailjetClient mailjetClient;
    @MockBean
    protected RestTemplate restTemplate;
    @SpyBean
    protected PasswordEncoder passwordEncoder;

    @PostConstruct
    public void prepare() {
        RestAssured.port = port;
        final String userToken = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(1)
                        .email("michaelfmnk@gmail.com")
                        .build());
        headers = new Headers(new Header(authProperties.getHeaderName(), userToken));

        final String badToken = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(-1)
                        .email("fake@fake.com")
                        .build());
        badHeaders = new Headers(new Header(authProperties.getHeaderName(), badToken));
    }

    @Test
    public void contextLoads() {
        Assertions.assertTrue(true);
    }

}
