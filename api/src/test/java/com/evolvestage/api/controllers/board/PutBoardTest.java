package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.docs.DocumentDto;
import com.evolvestage.api.dtos.BoardBriefDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.HttpServerErrorException;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class PutBoardTest extends BaseTest {

    @Test
    public void shouldUpdateBoard() throws JsonProcessingException {
        BoardBriefDto boardToUpdate = BoardBriefDto.builder()
                .backgroundId(UUID.randomUUID())
                .name("updated board name")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(boardToUpdate))
                .when()
                .put("/api/boards/1").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("name", equalTo("updated board name"))
                .body("background_id", equalTo(boardToUpdate.getBackgroundId().toString()))
                .body("background_url", endsWith("/docs-api/permanent/public/" + boardToUpdate.getBackgroundId()));

        verify(restTemplate, times(1))
                .exchange(
                        ArgumentMatchers.endsWith("permanent?file_id=00000000-0000-0000-0000-000000000001"),
                        ArgumentMatchers.eq(HttpMethod.DELETE),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(String.class)
                );
        verify(restTemplate, times(1))
                .exchange(
                        ArgumentMatchers.endsWith("/permanent"),
                        ArgumentMatchers.eq(HttpMethod.PUT),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(new ParameterizedTypeReference<List<DocumentDto>>() {})
                );

        assertThat(new Request(dataSource, "select * from boards where board_id = 1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("name").isEqualTo("updated board name")
                .value("background_id").isEqualTo(boardToUpdate.getBackgroundId());
    }

    @Test
    public void shouldDeleteBackground() throws JsonProcessingException {
        BoardBriefDto boardToUpdate = BoardBriefDto.builder()
                .name("updated board name")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(boardToUpdate))
                .when()
                .put("/api/boards/1").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("name", equalTo("updated board name"))
                .body("background_id", isEmptyOrNullString())
                .body("background_url", isEmptyOrNullString());
        verify(restTemplate, times(1))
                .exchange(
                        ArgumentMatchers.endsWith("permanent?file_id=00000000-0000-0000-0000-000000000001"),
                        ArgumentMatchers.eq(HttpMethod.DELETE),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(String.class)
                );
        verify(restTemplate, times(0))
                .exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.eq(HttpMethod.PUT),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(new ParameterizedTypeReference<List<DocumentDto>>() {})
                );
        assertThat(new Request(dataSource, "select * from boards where board_id = 1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("name").isEqualTo("updated board name")
                .value("background_id").isNull();
    }

    @Test
    public void shouldNotTouchBackgroundIfItWasNotChanged() throws JsonProcessingException {
        BoardBriefDto boardToUpdate = BoardBriefDto.builder()
                .backgroundId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .name("updated board name")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(boardToUpdate))
                .when()
                .put("/api/boards/1").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("name", equalTo("updated board name"))
                .body("background_id", equalTo(boardToUpdate.getBackgroundId().toString()))
                .body("background_url", endsWith("/docs-api/permanent/public/" + boardToUpdate.getBackgroundId()));
        verify(restTemplate, times(0))
                .exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.eq(HttpMethod.DELETE),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(String.class)
                );
        verify(restTemplate, times(0))
                .exchange(
                        ArgumentMatchers.anyString(),
                        ArgumentMatchers.eq(HttpMethod.PUT),
                        ArgumentMatchers.any(),
                        ArgumentMatchers.eq(new ParameterizedTypeReference<List<DocumentDto>>() {})
                );
        assertThat(new Request(dataSource, "select * from boards where board_id = 1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("name").isEqualTo("updated board name")
                .value("background_id").isEqualTo(boardToUpdate.getBackgroundId());
    }

    @Test
    public void shouldNotUpdateBoardOnValidation() throws JsonProcessingException {
        BoardBriefDto boardToUpdate = BoardBriefDto.builder()
                .backgroundId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(boardToUpdate))
                .when()
                .put("/api/boards/1").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }

    @Test
    public void restTemplateShouldRethrowExceptionAndDataShouldBeRestored() throws JsonProcessingException {
        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),
                ArgumentMatchers.eq(new ParameterizedTypeReference<List<DocumentDto>>() {})
        )).thenThrow(new HttpServerErrorException(org.springframework.http.HttpStatus.NOT_FOUND));
        BoardBriefDto boardToUpdate = BoardBriefDto.builder()
                .backgroundId(UUID.randomUUID())
                .name("updated board name")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(boardToUpdate))
                .when()
                .put("/api/boards/1").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        assertThat(new Request(dataSource, "select * from boards where board_id = 1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("name").isEqualTo("SUPERMEGA BOARD")
                .value("background_id").isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

}
