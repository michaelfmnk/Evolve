package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;

public class DeleteCardTest extends BaseTest {

    @Test
    public void shouldDeleteCard() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/1/cards/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        assertThat(new Request(dataSource, format("SELECT * FROM cards WHERE card_id=%s", 1)))
                .hasNumberOfRows(0);

    }

    @Test
    public void shouldNotDeleteCardOnForbidden() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .delete("/api/boards/2/cards/3")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
