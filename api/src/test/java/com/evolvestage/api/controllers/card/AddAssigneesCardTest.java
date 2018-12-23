package com.evolvestage.api.controllers.card;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.containers.ListContainer;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

public class AddAssigneesCardTest extends BaseTest {

    @Test
    public void shouldAssignPeople() {
        List<Integer> newAssignees = Arrays.asList(1, 2, 3);
        ListContainer<Integer> list = new ListContainer<>(newAssignees);
        given()
                .auth()
                .body(list)
                .when()
                .post("/api/boards/1/columns/1/cards/1/assignees")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", notNullValue())
                .body("content", equalTo("card 1 from col 1"))
                .body("column_id", equalTo(1))
                .body("title", equalTo("card 1"))
                .body("users", hasSize(3));

        assertThat(new Request(dataSource, "select * from cards_users where card_id = 1 order by user_id"))
                .hasNumberOfRows(3)
                .row(0).value("user_id").isEqualTo(1)
                .row(1).value("user_id").isEqualTo(2)
                .row(2).value("user_id").isEqualTo(3);
    }

    @Test
    public void shouldNotAssignPeople() {
        List<Integer> newAssignees = Arrays.asList(2, 3, 4);
        ListContainer<Integer> list = new ListContainer<>(newAssignees);
        given()
                .auth()
                .body(list)
                .when()
                .post("/api/boards/1/columns/1/cards/1/assignees")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("detail", equalTo("One of the persons is not a collaborator"));
    }


}
