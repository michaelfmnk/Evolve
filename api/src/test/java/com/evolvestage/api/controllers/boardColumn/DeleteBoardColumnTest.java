package com.evolvestage.api.controllers.boardColumn;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;

public class DeleteBoardColumnTest extends BaseTest {

    @Test
    public void shouldDeleteBoardColumn() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/1/columns/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, format("SELECT * FROM columns WHERE column_id=%s", 1)))
                .hasNumberOfRows(0);
    }

    @Test
    public void shouldNotDeleteBoardColumn() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/2/columns/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
