package com.evolvestage.api.controllers;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CommonControllerTest extends BaseTest {

    @Test
    public void testVersion() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/version")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("latest"));

        given()
                .contentType(ContentType.JSON)
                .headers(badHeaders)
                .when()
                .get("/api/version")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("latest"));
    }

    @Test
    public void shouldGetNotFound() {
        given()
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/bad-url")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
