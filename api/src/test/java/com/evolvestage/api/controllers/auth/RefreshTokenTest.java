package com.evolvestage.api.controllers.auth;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.AuthRequest;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.sql.SQLException;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RefreshTokenTest extends BaseTest {

    @Test
    public void shouldRefreshToken() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("michaelfmnk@gmail.com")
                .password("test")
                .build();
        given()
                .noAuth()
                .body(authRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .body("user_id", equalTo(1));

        given()
                .auth()
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
                .auth()
                .when()
                .get("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("detail", equalTo("This token can not be refreshed"));

    }
}
