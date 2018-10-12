package com.dreamteam.api.controllers.label;

import com.dreamteam.api.BaseTest;
import com.dreamteam.api.dtos.LabelDto;
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

public class PostLabelTest extends BaseTest {

    @Test
    public void shouldCreateLabel() throws IOException {
        LabelDto label = LabelDto.builder()
                .color("#d10cc3")
                .build();
        String json = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(label))
                .when()
                .post("/api/boards/1/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("color", equalTo("#d10cc3"))
                .extract().response().body().asString();
        LabelDto response = objectMapper.readValue(json, LabelDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM LABELS WHERE label_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("color").isEqualTo("#d10cc3")
                .value("board_id").isEqualTo(1);
    }

    @Test
    public void shouldNotCreateLabel() throws IOException {
        LabelDto label = new LabelDto();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(label))
                .when()
                .post("/api/boards/1/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("color: must not be blank"));
    }

    @Test
    public void shouldNotCreateLabelWithoutPermission() throws IOException {
        LabelDto label = LabelDto.builder()
                .color("#d10cc3")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(label))
                .when()
                .post("/api/boards/4/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("detail", equalTo("Access is denied"));
    }
}
