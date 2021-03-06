package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.CardBriefDto;
import com.evolvestage.api.dtos.CardDto;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostCardTest extends BaseTest{

    @Test
    public void shouldCreateCard() {
        CardBriefDto card = CardBriefDto.builder()
                .content("NEW CARD")
                .title("NEW CARD TITLE")
                .build();
        CardDto response = given()
                .auth()
                .body(card)
                .when()
                .post("/api/boards/1/columns/1/cards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("content", equalTo("NEW CARD"))
                .body("column_id", equalTo(1))
                .body("title", equalTo("NEW CARD TITLE"))
                .extract().as(CardDto.class);
        assertThat(new Request(dataSource, format("SELECT * FROM CARDS WHERE card_id=%s", response.getId())))
                .hasNumberOfRows(1)
                .row(0).value("content").isEqualTo("NEW CARD")
                .row(0).value("title").isEqualTo("NEW CARD TITLE")
                .value("column_id").isEqualTo(1);
    }

    @Test
    public void shouldNotCreateCard() {
        CardBriefDto card = new CardBriefDto();
        given()
                .auth()
                .body(card)
                .when()
                .post("/api/boards/1/columns/1/cards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("detail", equalTo("title: must not be blank"));
    }

    @Test
    public void shouldNotCreateCardWithoutPermission() {
        CardBriefDto card = CardBriefDto.builder()
                .title("NEW CARD TITLE")
                .build();
        given()
                .auth()
                .body(card)
                .when()
                .post("/api/boards/2/columns/4/cards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body("detail", equalTo("Access is denied"));
    }

    @Test
    public void shouldNotCreateCardInTheNotValidColumn() {
        CardBriefDto card = CardBriefDto.builder()
                .title("NEW CARD TITLE")
                .build();
        given()
                .auth()
                .body(card)
                .when()
                .post("/api/boards/1/columns/4/cards")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("detail", equalTo("Column was not found"));
    }

}
