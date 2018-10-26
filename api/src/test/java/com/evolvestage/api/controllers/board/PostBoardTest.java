package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.BoardDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostBoardTest extends BaseTest {

    @Test
    public void shouldCreateBoard() throws IOException {
        BoardDto board = BoardDto.builder()
                .name("NEW BOARD")
                .backgroundId(UUID.randomUUID())
                .build();
        String json = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(board))
                .when()
                .post("/api/boards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("name", equalTo("NEW BOARD"))
                .extract().response().body().asString();
        BoardDto response = objectMapper.readValue(json, BoardDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM BOARDS WHERE board_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("name").isEqualTo("NEW BOARD")
                            .value("owner_id").isEqualTo(1)
                            .value("background_id").isEqualTo(response.getBackgroundId());

    }

    @Test
    public void shouldNotCreateBoardOnValidation() throws IOException {
        BoardDto board = BoardDto.builder()
                .backgroundId(UUID.randomUUID())
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(board))
                .when()
                .post("/api/boards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("name: must not be blank"));
    }

    @Test
    public void shouldNotCreateBoardOnUnauthorized() throws IOException {
        BoardDto board = BoardDto.builder()
                .backgroundId(UUID.randomUUID())
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(board))
                .when()
                .post("/api/boards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

}
