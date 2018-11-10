package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.docs.DocumentDto;
import com.evolvestage.api.dtos.BoardDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
                .body("background_url", endsWith("/docs-api/permanent/public/" + board.getBackgroundId().toString()))
                .extract().response().body().asString();
        BoardDto response = objectMapper.readValue(json, BoardDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM boards WHERE board_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("name").isEqualTo("NEW BOARD")
                            .value("owner_id").isEqualTo(1)
                            .value("background_id").isEqualTo(response.getBackgroundId());
        assertThat(new Request(dataSource, "SELECT * FROM activities"))
                .hasNumberOfRows(1)
                .row(0).value("type").isEqualTo("BOARD_CREATED")
                .value("actor_id").isEqualTo(1)
                .value("data").isNotNull()
                .value("board_id").isEqualTo(response.getId());
        verify(restTemplate, times(1))
                .exchange(
                        ArgumentMatchers.endsWith("/permanent"),
                        ArgumentMatchers.eq(HttpMethod.PUT),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(new ParameterizedTypeReference<List<DocumentDto>>() {})
                );
    }

    @Test
    public void shouldNotSendRequestToMoveFileOnCreateBoardWithDefaulBackground() throws IOException {
        BoardDto board = BoardDto.builder()
                .name("NEW BOARD")
                .backgroundId(UUID.fromString("f34d29fe-2136-406b-8bf5-08c97e3eb958"))
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
                .statusCode(HttpStatus.SC_OK);
        verify(restTemplate, times(0))
                .exchange(
                        ArgumentMatchers.contains("/permanent"),
                        ArgumentMatchers.eq(HttpMethod.PUT),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(new ParameterizedTypeReference<List<DocumentDto>>() {})
                );
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
