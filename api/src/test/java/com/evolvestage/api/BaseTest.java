package com.evolvestage.api;

import com.evolvestage.api.config.RedisTestConfig;
import com.evolvestage.api.properties.AuthProperties;
import com.evolvestage.api.security.JwtTokenUtil;
import com.evolvestage.api.security.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailjet.client.MailjetClient;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.GenericContainer;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@ExtendWith(SpringExtension.class)
@SqlGroup({@Sql("classpath:test-clean.sql"), @Sql})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {ApiApplication.class, RedisTestConfig.class})
public abstract class BaseTest {


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
    @Autowired
    protected StringRedisTemplate redisTemplate;

    /**
     * Redis
     */
    public static final int REDIS_PORT = 6379;
    public static GenericContainer redis = new GenericContainer("redis:3.0.2").withExposedPorts(REDIS_PORT);
    static {
        redis.start();
    }

    @PostConstruct
    public void prepare() {
        RestAssured.port = port;
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig()
                        .jackson2ObjectMapperFactory((aClass, s) -> objectMapper));
    }



    protected RequestSpecificationWrapper given() {
        return new RequestSpecificationWrapper();
    }

    protected class RequestSpecificationWrapper {

        public RequestSpecification auth() {
            return auth(1, "michaelfmnk@gmail.com");
        }

        public RequestSpecification badAuth() {
            return auth(-1, "fake@auth.com");
        }

        public RequestSpecification noAuth() {
            return givenBaseSpec();
        }

        public RequestSpecification auth(Integer userId, String email) {
            String userToken = jwtTokenUtil.generateToken(
                    JwtUser.builder()
                            .id(userId)
                            .email(email)
                            .build());
            return givenBaseSpec().header(new Header(authProperties.getHeaderName(), userToken));
        }

        private RequestSpecification givenBaseSpec() {
            return RestAssured.given().log().all()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON);
        }
    }

}
