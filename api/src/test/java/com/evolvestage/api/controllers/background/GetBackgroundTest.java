package com.evolvestage.api.controllers.background;

import com.evolvestage.api.BaseTest;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static org.hamcrest.Matchers.*;

public class GetBackgroundTest extends BaseTest {

    @Test
    public void shouldGetCommonBackgroundsAndFormUrl() {
        given()
                .auth()
                .when()
                .get("/api/backgrounds").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(greaterThanOrEqualTo(12)))
                .body("data[0].background_id", equalTo("111d2419-acc3-4b35-ba49-c5938d0f524d"))
                .body("data[0].background_url", endsWith("docs-api/permanent/public/111d2419-acc3-4b35-ba49-c5938d0f524d"));
    }

    @Test
    public void shouldWorkPagination() {
        given()
                .auth()
                .param("size", 4)
                .when()
                .get("/api/backgrounds").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(4))
                .body("total_elements", equalTo(13))
                .body("total_pages", equalTo(4));

        given()
                .auth()
                .param("page", 1000)
                .param("size", 4)
                .when()
                .get("/api/backgrounds").prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("data", hasSize(0));
    }
}
