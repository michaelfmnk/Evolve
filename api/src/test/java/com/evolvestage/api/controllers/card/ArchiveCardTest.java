package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class ArchiveCardTest extends BaseTest {

    @Test
    public void shouldArchiveCard() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .patch("/api/boards/1/cards/1/archive")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void shouldNotGetCard() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .patch("/api/boards/2/cards/3/archive")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
