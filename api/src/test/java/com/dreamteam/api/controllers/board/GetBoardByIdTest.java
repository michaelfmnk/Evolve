package com.dreamteam.api.controllers.board;

import com.dreamteam.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetBoardByIdTest extends BaseTest {
    @Test
    public void shouldGetBoardInfo() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/boards/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("name", equalTo("SUPERMEGA BOARD"))
                .body("owner_id", equalTo(1))
                .body("columns", hasSize(3));
    }

    @Test
    public void shouldNotGetBoardInfo() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/boards/6")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("detail", equalTo("You are not a collaborator"));
    }
}
