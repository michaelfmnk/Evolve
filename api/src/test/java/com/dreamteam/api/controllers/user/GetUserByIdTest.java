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
import static org.hamcrest.core.IsNull.notNullValue;

public class GetUserByIdTest extends BaseTest {
    private UserService userService;
    private ConverterService converterService;

    @Test
    public void shouldGetUserInfo() throws IOException {

        String json = given()
                .accept(ContentType.JSON)
                .headers(headers)
                .when()
                .get("/api/users/{user_id}")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("id", equalTo(1))
                .body("firstName", equalTo("Kateryna"))
                .body("lastName", equalTo("Kanivets"))
                .body("email", notNullValue())
                //something else
                .extract().response().body().asString();

        UserDto response = objectMapper.readValue(json, UserDto.class);
        System.out.println(response);
    }

    @Test
    public void shouldGetUserBriefInfo() {

    }
}
