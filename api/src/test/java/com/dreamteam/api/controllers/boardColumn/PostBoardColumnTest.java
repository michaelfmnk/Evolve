package com.dreamteam.api.controllers.boardColumn;

import com.dreamteam.api.BaseTest;
import com.dreamteam.api.dtos.BoardColumnDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostBoardColumnTest  extends BaseTest{

    @Test
    public void shouldCreateBoardColumn() throws IOException {
        BoardColumnDto column = BoardColumnDto.builder()
                .name("NEW COLUMN")
                .build();
        String json = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .post("/api/boards/1/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("name", equalTo("NEW COLUMN"))
                .extract().response().body().asString();
        BoardColumnDto response = objectMapper.readValue(json, BoardColumnDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM COLUMNS WHERE column_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("name").isEqualTo("NEW COLUMN")
                .value("board_id").isEqualTo(1);
    }

    @Test
    public void shouldNotCreateColumn() throws IOException {
        BoardColumnDto column = new BoardColumnDto();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .post("/api/boards/1/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("name: must not be blank"));
    }

    @Test
    public void shouldNotCreateColumnOnUnauthorized() throws IOException {
        BoardColumnDto column = new BoardColumnDto();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .post("/api/boards/1/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
