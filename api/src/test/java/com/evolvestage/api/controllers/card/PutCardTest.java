package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.CardBriefDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PutCardTest extends BaseTest{

    @Test
    public void shouldUpdateCard() throws IOException {
        CardBriefDto card = CardBriefDto.builder()
                .content("UPDATED CARD")
                .title("UPDATED CARD TITLE")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(card))
                .when()
                .put("/api/boards/1/columns/1/cards/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("content", equalTo("UPDATED CARD"))
                .body("column_id", equalTo(1))
                .body("title", equalTo("UPDATED CARD TITLE"));

        assertThat(new Request(dataSource, "SELECT * FROM cards WHERE card_id=1"))
                .hasNumberOfRows(1)
                .row(0).value("content").isEqualTo("UPDATED CARD")
                .row(0).value("title").isEqualTo("UPDATED CARD TITLE")
                .value("column_id").isEqualTo(1);
    }

    @Test
    public void shouldGet422() throws IOException {
        CardBriefDto card = CardBriefDto.builder()
                .content("UPDATED CARD")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(card))
                .when()
                .put("/api/boards/1/columns/1/cards/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }


    @Test
    public void shouldGetForbidden() throws IOException {
        CardBriefDto card = CardBriefDto.builder()
                .content("UPDATED CARD")
                .title("UPDATED CARD TITLE")
                .build();
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(objectMapper.writeValueAsBytes(card))
                .when()
                .put("/api/boards/2/columns/1/cards/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }


}
