package com.evolvestage.api.controllers.boardColumn;

import com.evolvestage.api.BaseTest;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;

public class DeleteBoardColumnTest extends BaseTest {

    @Test
    public void shouldDeleteBoardColumn() {
        given()
                .auth()
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
                .auth()
                .when()
                .delete("/api/boards/2/columns/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
