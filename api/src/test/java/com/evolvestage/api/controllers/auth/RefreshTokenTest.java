package com.evolvestage.api.controllers.auth;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.sql.SQLException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RefreshTokenTest extends BaseTest {

    @Test
    public void shouldRefreshToken() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .email("michaelfmnk@gmail.com")
                .password("test")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .body("user_id", equalTo(1));

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue());

    }

    @Test
    public void shouldNotRefreshToken() throws SQLException {
        dataSource.getConnection()
                .prepareStatement("update users set last_password_reset_date = '2050-12-12' where user_id = 1")
                .execute();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("detail", equalTo("This token can not be refreshed"));

    }
}
