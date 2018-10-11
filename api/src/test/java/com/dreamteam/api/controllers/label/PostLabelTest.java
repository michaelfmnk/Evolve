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
        LabelDto column = LabelDto.builder()
                .name("NEW LABEL")
                .color("#d10cc3")
                .build();
        String json = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(column))
                .when()
                .post("/api/boards/1/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("name", equalTo("NEW COLUMN"))
                .body("name", equalTo("#d10cc3"))
                .extract().response().body().asString();
        LabelDto response = objectMapper.readValue(json, LabelDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM LABELS WHERE column_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("name").isEqualTo("NEW LABEL")
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
                .body("detail", equalTo("name: must not be blank"));
    }
}
