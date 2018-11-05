package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.HttpMethod;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
                .statusCode(HttpStatus.SC_NO_CONTENT);
        assertThat(new Request(dataSource, format("SELECT * FROM boards WHERE board_id=%s", 1)))
                .hasNumberOfRows(0);
        verify(restTemplate, times(1))
                .exchange(
                        ArgumentMatchers.endsWith("permanent?file_id=1e2ef350-dd39-11e8-9f8b-f2801f1b9fd1"),
                        ArgumentMatchers.eq(HttpMethod.DELETE),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(String.class)
                );
    }

    @Test
    public void shouldDeleteBoardAndNotDeleteDefaultBackground() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/4")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        assertThat(new Request(dataSource, format("SELECT * FROM boards WHERE board_id=%s", 4)))
                .hasNumberOfRows(0);
        verify(restTemplate, times(0))
                .exchange(
                        ArgumentMatchers.contains(""),
                        ArgumentMatchers.eq(HttpMethod.DELETE),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(String.class)
                );
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
