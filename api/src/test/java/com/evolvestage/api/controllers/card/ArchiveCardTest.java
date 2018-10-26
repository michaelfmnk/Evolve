package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

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
        assertThat(new Request(dataSource, format("SELECT * FROM cards WHERE card_id=%s", 1)))
                .row(0).value("title").isEqualTo("card 1")
                .value("archived").isEqualTo(true);

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
