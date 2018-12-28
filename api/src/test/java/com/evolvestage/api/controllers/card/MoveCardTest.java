package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class MoveCardTest extends BaseTest {

    @Test
    public void shouldMoveCardToAnotherColumn() {
       given()
                .auth()
                .when()
                .patch("/api/boards/1/columns/1/cards/1/move/2")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("content", equalTo("card 1 from col 1"))
                .body("column_id", equalTo(2))
                .body("title", equalTo("card 1"));

        assertThat(new Request(dataSource, "select * from cards where card_id = 1"))
                .hasNumberOfRows(1)
                .row(0).value("column_id").isEqualTo(2);
    }

    @Test
    public void shouldNotMoveCardToAnotherColumnWithNotFoundColumn() {
        given()
                .auth()
                .when()
                .patch("/api/boards/1/columns/1/cards/1/move/4")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("detail", equalTo("Column was not found"));

        assertThat(new Request(dataSource, "select * from cards where card_id = 1"))
                .hasNumberOfRows(1)
                .row(0).value("column_id").isEqualTo(1);
    }

    @Test
    public void shouldGetCardNotFound() {
        given()
                .auth()
                .when()
                .patch("/api/boards/2/columns/1/cards/1/move/4")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("detail", equalTo("Card was not found"));

        assertThat(new Request(dataSource, "select * from cards where card_id = 1"))
                .hasNumberOfRows(1)
                .row(0).value("column_id").isEqualTo(1);
    }

}
