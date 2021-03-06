package com.evolvestage.api.controllers.auth;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginTest extends BaseTest {

    @Test
    public void shouldLogin() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("michaelfmnk@gmail.com")
                .password("test")
                .build();
        given()
                .noAuth()
                .body(authRequest)
                .when()
                .post("/api/auth/login").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue())
                .body("user_id", equalTo(1));
    }

    @Test
    public void shouldNotLoginWithBadCredentials() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("michaelfmnk@gmail.com")
                .password("badPassword")
                .build();
        given()
                .noAuth()
                .body(authRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("title", equalTo("UNAUTHORIZED"))
                .body("status", equalTo(401))
                .body("detail", equalTo("Bad credentials"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.authentication.BadCredentialsException"));

        authRequest.setPassword(null);
        given()
                .noAuth()
                .body(authRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .body("title", equalTo("UNAUTHORIZED"))
                .body("status", equalTo(401))
                .body("detail", equalTo("Bad credentials"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.authentication.BadCredentialsException"));
    }

    @Test
    public void shouldNotLoginNotEnabledUser() throws JsonProcessingException {
        AuthRequest authRequest = AuthRequest.builder()
                .email("steven@gmail.com")
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
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
                .body("title", equalTo("METHOD_NOT_ALLOWED"))
                .body("status", equalTo(405))
                .body("detail", equalTo("User is disabled"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.authentication.DisabledException"));
    }

    @Test
    public void shouldFailLoginOnUserNotFound() {
        AuthRequest authRequest = AuthRequest.builder()
                .email("fakeLogin")
                .build();
        given()
                .noAuth()
                .body(authRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("title", equalTo("NOT_FOUND"))
                .body("status", equalTo(404))
                .body("detail", equalTo("User was not found"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("javax.persistence.EntityNotFoundException"));
    }

    @Test
    public void shouldFailLoginOnUnprocessableEntity() {
        AuthRequest authRequest = AuthRequest.builder()
                .password("asldkfj;alksdjf;laksjd;flkaj")
                .build();
        given()
                .noAuth()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(authRequest)
                .when()
                .post("/api/auth/login")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("title", equalTo("UNPROCESSABLE_ENTITY"))
                .body("status", equalTo(422))
                .body("detail", equalTo("login is not provided"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.security.authentication.InternalAuthenticationServiceException"));

    }

}
