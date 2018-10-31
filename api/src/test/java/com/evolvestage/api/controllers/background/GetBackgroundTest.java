package com.evolvestage.api.controllers.background;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetBackgroundTest extends BaseTest {

    @Test
    public void shouldGetCommonBackgroundsAndFormUrl() {
        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/backgrounds").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("$", hasSize(4))
                .body("[0].background_id", equalTo("1e2eeb8a-dd39-11e8-9f8b-f2801f1b9fd1"))
                .body("[0].background_url", equalTo("http://sevolve-stage.com:80/docs-api/permanent/public/1e2eeb8a-dd39-11e8-9f8b-f2801f1b9fd1"));

    }
}
