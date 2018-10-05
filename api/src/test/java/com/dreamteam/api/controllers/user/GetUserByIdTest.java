package com.dreamteam.api.controllers.user;

import com.dreamteam.api.BaseTest;
import com.dreamteam.api.dtos.UserDto;
import com.dreamteam.api.services.ConverterService;
import com.dreamteam.api.services.UserService;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class GetUserByIdTest extends BaseTest {

    @Test
    public void shouldGetUserInfo() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/users/1")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("first_name", equalTo("Kateryna"))
                .body("last_name", equalTo("Kanivets"))
                .body("email", notNullValue())
                .body("own_boards", hasSize(1))
                .body("own_boards[0].name", equalTo("board first"))
                .body("joined_boards", hasSize(2))
                .body("joined_boards[0].name", equalTo("board first"))
                .body("joined_boards[1].name", equalTo("board second"));
    }

    @Test
    public void shouldFailGetUserInfoOnNotFound() throws IOException {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/users/2000")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("detail", equalTo("User was not found"));
    }

    @Test
    public void shouldGetUserBriefInfo() {
        given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/users/2")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(2))
                .body("first_name", equalTo("Nick"))
                .body("last_name", equalTo("Brown"))
                .body("own_boards", nullValue())
                .body("joined_boards", nullValue());
    }
}
