package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RemoveAssigneeCardTest extends BaseTest {

    @Test
    public void shouldUnassignPerson() {
        assertThat(new Request(dataSource, "select * from cards_users where card_id = 1 and user_id = 2"))
                .hasNumberOfRows(1);

        given()
                .auth()
                .when()
                .delete("/api/boards/1/columns/1/cards/1/assignees/2")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertThat(new Request(dataSource, "select * from cards_users where card_id = 1 and user_id = 2"))
                .hasNumberOfRows(0);
    }

    @Test
    public void shouldGetNotFoundPerson() {
        given()
                .auth()
                .when()
                .delete("/api/boards/1/columns/1/cards/1/assignees/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("detail", equalTo("Such user was not found among this card assignees"));
    }

}
