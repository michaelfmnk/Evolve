package com.evolvestage.api.controllers.boardColumn;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.BoardColumnDto;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostBoardColumnTest  extends BaseTest {

    @Test
    public void shouldCreateBoardColumn() {
        BoardColumnDto column = BoardColumnDto.builder()
                .name("NEW COLUMN")
                .build();
        BoardColumnDto response = given()
                .auth()
                .body(column)
                .when()
                .post("/api/boards/1/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("name", equalTo("NEW COLUMN"))
                .extract().as(BoardColumnDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM COLUMNS WHERE column_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("name").isEqualTo("NEW COLUMN")
                .value("board_id").isEqualTo(1);
    }

    @Test
    public void shouldNotCreateColumn() {
        BoardColumnDto column = new BoardColumnDto();
        given()
                .auth()
                .body(column)
                .when()
                .post("/api/boards/1/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("name: must not be blank"));
    }

    @Test
    public void shouldNotCreateColumnOnUnauthorized() {
        BoardColumnDto column = new BoardColumnDto();
        given()
                .noAuth()
                .body(column)
                .when()
                .post("/api/boards/1/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    public void shouldNotAllowCreateColumnWithoutBorder() {
        BoardColumnDto column = BoardColumnDto.builder()
                .name("NEW COLUMN")
                .build();
        given()
                .auth()
                .body(column)
                .when()
                .post("/api/boards/5/columns")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
