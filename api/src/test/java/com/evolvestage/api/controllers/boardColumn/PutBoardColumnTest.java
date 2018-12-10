package com.evolvestage.api.controllers.boardColumn;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.BoardColumnDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PutBoardColumnTest extends BaseTest {

    @Test
    public void shouldRenameColumn() throws IOException {
        BoardColumnDto column = BoardColumnDto.builder()
                .name("NEW COLUMN NAME")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .put("/api/boards/1/columns/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("name", equalTo("NEW COLUMN NAME"));

        assertThat(new Request(dataSource, "SELECT * FROM COLUMNS WHERE column_id=1"))
                .hasNumberOfRows(1)
                .row(0).value("name").isEqualTo("NEW COLUMN NAME")
                .value("board_id").isEqualTo(1);
    }

    @Test
    public void columnCanNotHaveEmptyName() throws IOException {
        BoardColumnDto column = BoardColumnDto.builder()
                .name("")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .put("/api/boards/1/columns/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("name: must not be blank"));
    }

    @Test
    public void shouldNotAllowRenameColumn() throws IOException {
        BoardColumnDto column = BoardColumnDto.builder()
                .name("NEW COLUMN NAME")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .put("/api/boards/4/columns/4")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

}
