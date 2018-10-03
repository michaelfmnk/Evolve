package com.dreamteam.api.controllers.auth;

import com.dreamteam.api.BaseTest;
import com.dreamteam.api.dtos.SignUpDto;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegisterTest extends BaseTest {

    @Test
    public void shouldRegister() throws Throwable {
        SignUpDto authRequest = SignUpDto.builder()
                .email("meteormf99@gmail.com")
                .password("newPassword12")
                .firstName("FN")
                .lastName("LN")
                .build();


        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .body("id", notNullValue())
                .body("email", equalTo("meteormf99@gmail.com"));


        assertThat(new Request(dataSource,
                "SELECT enabled, last_password_reset_date FROM users " +
                        "WHERE email='meteormf99@gmail.com' AND " +
                        "first_name='FN' AND " +
                        "last_name='LN'"))
                .hasNumberOfRows(1)
                .row(0)
                .value("last_password_reset_date").isNotNull()
                .value("enabled").isTrue();
    }

    @Test
    public void shouldRegisterOnUnprocessableEntity() throws Throwable {
        SignUpDto authRequest = SignUpDto.builder()
                .email("meteormf99gmail.com")
                .password("newPassword12")
                .firstName("FN")
                .lastName("LN")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("title", equalTo("UNPROCESSABLE_ENTITY"))
                .body("status", equalTo(422))
                .body("detail", equalTo("email: must be a well-formed email address"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"));

        authRequest = SignUpDto.builder()
                .email("meteormf99@gmail.com")
                .password("n")
                .firstName("FN")
                .lastName("LN")
                .build();

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(objectMapper.writeValueAsBytes(authRequest))
                .when()
                .post("/api/auth/sign-up")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY)
                .body("title", equalTo("UNPROCESSABLE_ENTITY"))
                .body("status", equalTo(422))
                .body("detail", equalTo("password: Invalid password"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("org.springframework.web.bind.MethodArgumentNotValidException"));
    }

}
