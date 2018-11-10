package com.evolvestage.api.controllers.activities;

import com.evolvestage.api.BaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class GetBoardActivitiesTest extends BaseTest {

    @Test
    public void shouldGetActivities() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .param("page", "0")
                .param("size", "2")
                .when()
                .get("/api/boards/1/activities")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(2))
                .body("data[0].actor.avatar_id", equalTo("0485de66-c013-11e8-a355-529269fb1459"))
                .body("data[0].data.data", equalTo("data1"))
                .body("data[1].data.data", equalTo("data2"))
                .body("total_elements", equalTo(3))
                .body("total_pages", equalTo(2));
    }

    @Test
    public void shouldNotGetActivities() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .param("page", "1")
                .param("size", "2")
                .when()
                .get("/api/boards/3/activities")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }
}
