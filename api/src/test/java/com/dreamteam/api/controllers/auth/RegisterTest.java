package com.dreamteam.api.controllers.auth;

import com.dreamteam.api.BaseTest;
import com.dreamteam.api.dtos.SignUpDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.errors.MailjetException;
import io.restassured.http.ContentType;
import org.assertj.db.type.Request;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegisterTest extends BaseTest {

    @Test
    public void shouldRegister() throws Throwable {
        doReturn("passwordHash")
                .when(passwordEncoder).encode(any(CharSequence.class));
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

        verify(mailjetClient, times(1)).post(any(MailjetRequest.class));

        assertThat(new Request(dataSource,
                "SELECT * FROM users " +
                        "WHERE email='meteormf99@gmail.com' AND " +
                        "first_name='FN' AND " +
                        "last_name='LN'"))
                .hasNumberOfRows(1)
                .row(0)
                .value("last_password_reset_date").isNotNull()
                .value("password").isEqualTo("passwordHash")
                .value("enabled").isFalse();

        assertThat(new Request(dataSource,
                "SELECT * FROM users_authorities WHERE user_id=1000 AND authority_id=2"))
                .hasNumberOfRows(1);
        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE user_id=1000"))
                .hasNumberOfRows(1)
                .row(0)
                .value("verification_code").isNotNull();
    }

    @Test
    public void shouldReuseNotEnabledUserOnSecondSignUp() throws JsonProcessingException {
        doReturn("passwordHash")
                .when(passwordEncoder).encode(any(CharSequence.class));
        SignUpDto authRequest = SignUpDto.builder()
                .email("steven@gmail.com")
                .password("newPassword12")
                .firstName("Stevie")
                .lastName("Stevens")
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
                .body("id", equalTo(4))
                .body("email", equalTo("steven@gmail.com"));

        assertThat(new Request(dataSource,
                "SELECT * FROM users WHERE user_id = 4"))
                .hasNumberOfRows(1)
                .row(0)
                .value("email").isEqualTo("steven@gmail.com")
                .value("first_name").isEqualTo("Stevie")
                .value("last_name").isEqualTo("Stevens")
                .value("last_name").isEqualTo("Stevens")
                .value("password").isEqualTo("passwordHash")
                .value("last_password_reset_date").isNotNull()
                .value("enabled").isFalse();

        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE user_id=4"))
                .hasNumberOfRows(1)
                .row(0)
                .value("verification_code").isNotNull();
    }

    @Test
    public void shouldFailRegisterIfNotPossibleToSendEmail() throws Throwable {
        when(mailjetClient.post(any(MailjetRequest.class))).thenThrow(new MailjetException("error"));

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
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body("title", equalTo("INTERNAL_SERVER_ERROR"))
                .body("status", equalTo(500))
                .body("detail", equalTo("Oops. Something went wrong while sending verification email. Try again later"))
                .body("timestamp", notNullValue())
                .body("dev_message", equalTo("java.lang.RuntimeException"));
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
