package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class DeleteBoardTest extends BaseTest {
    @Test
    public void shouldDeleteBoard() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldNotDeleteBoard() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
