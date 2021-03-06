package com.evolvestage.api.controllers.label;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.LabelDto;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class PostLabelTest extends BaseTest {

    @Test
    public void shouldCreateLabel() {
        LabelDto label = LabelDto.builder()
                .color("#d10cc3")
                .build();
        LabelDto response = given()
                .auth()
                .body(label)
                .when()
                .post("/api/boards/1/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("color", equalTo("#d10cc3"))
                .extract().as(LabelDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM LABELS WHERE label_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("color").isEqualTo("#d10cc3")
                .value("board_id").isEqualTo(1);
    }

    @Test
    public void shouldNotCreateLabelOnValidation() {
        LabelDto label = new LabelDto();
        given()
                .auth()
                .body(label)
                .when()
                .post("/api/boards/1/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("color: must not be blank"));

        label = LabelDto.builder()
                .color("badColor")
                .build();
        given()
                .auth()
                .body(label)
                .when()
                .post("/api/boards/1/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", containsString("color: must match"));
    }

    @Test
    public void shouldNotCreateLabelWithoutPermission() {
        LabelDto label = LabelDto.builder()
                .color("#d10cc3")
                .build();
        given()
                .auth()
                .body(label)
                .when()
                .post("/api/boards/4/labels")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("detail", equalTo("Access is denied"));
    }
}
