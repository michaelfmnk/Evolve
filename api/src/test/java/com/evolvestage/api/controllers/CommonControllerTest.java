package com.evolvestage.api.controllers;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.equalTo;

public class CommonControllerTest extends BaseTest {

    @Test
    public void testVersion() {
        given()
                .noAuth()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/version")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("latest"));

        given()
                .badAuth()
                .when()
                .get("/api/version")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("latest"));
    }

    @Test
    public void shouldGetNotFound() {
        given()
                .auth()
                .when()
                .get("/api/bad-url")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
