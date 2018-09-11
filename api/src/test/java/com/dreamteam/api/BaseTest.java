package com.dreamteam.api;

import io.restassured.RestAssured;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringJUnit4ClassRunner.class)
@SqlGroup({@Sql("classpath:test-clean.sql"), @Sql})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

    @LocalServerPort
    private Integer port;

    @PostConstruct
    public void prepare() {
        RestAssured.port = port;
    }

}
