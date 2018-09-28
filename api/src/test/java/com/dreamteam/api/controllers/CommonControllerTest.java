package com.dreamteam.api.controllers;

import com.dreamteam.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SqlGroup(value = {})
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
    }
}
