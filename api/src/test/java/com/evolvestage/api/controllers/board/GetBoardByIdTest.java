package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
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
                .body("columns", hasSize(3))
                .body("columns[0].name", equalTo("column 1"))
                .body("columns[0].cards", hasSize(2))
                .body("columns[0].cards[0].labels[1].color", equalTo("#ba3c3c"))
                .body("columns[0].cards[0].labels", hasSize(2))
                .body("columns[0].cards[0].users", hasSize(1))
                .body("columns[0].cards[0].order", equalTo(1))
                .body("columns[0].cards[0].content", equalTo("card 1 from col 1"))
                .body("columns[0].cards[0].users[0].first_name", equalTo("Michael"));
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
                .body("detail", equalTo("Access is denied"));
    }
}
