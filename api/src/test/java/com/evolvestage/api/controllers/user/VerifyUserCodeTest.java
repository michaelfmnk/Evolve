package com.evolvestage.api.controllers.user;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.VerificationCodeDto;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;


public class VerifyUserCodeTest extends BaseTest {

    @Test
    public void shouldVerifyCodeAndReturnValidToken() {
        VerificationCodeDto codeContainer = new VerificationCodeDto("123321");
        given()
                .noAuth()
                .body(codeContainer)
                .when()
                .post("api/users/4/verify")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("token", notNullValue());

        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE verification_code='123321' AND user_id=4"))
                .hasNumberOfRows(0);

        assertThat(new Request(dataSource,
                "SELECT enabled FROM users WHERE user_id=4"))
                .hasNumberOfRows(1)
                .row(0)
                .value("enabled").isEqualTo(true);
    }

    @Test
    public void shouldNotVerifyCode() {
        VerificationCodeDto codeContainer = new VerificationCodeDto("123399");
        given()
                .noAuth()
                .body(codeContainer)
                .when()
                .post("api/users/4/verify")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("title", equalTo("BAD_REQUEST"))
                .body("status", equalTo(400))
                .body("detail", equalTo("Verification code is not valid"))
                .body("timestamp", notNullValue())
                .body("dev_message", endsWith("BadRequestException"));

        assertThat(new Request(dataSource,
                "SELECT * FROM verification_codes WHERE verification_code='123321' AND user_id=4"))
                .hasNumberOfRows(1);

        assertThat(new Request(dataSource,
                "SELECT enabled FROM users WHERE user_id=4"))
                .hasNumberOfRows(1)
                .row(0)
                .value("enabled").isEqualTo(false);
    }

    @Test
    public void shouldFailVerifyCodeOnUserEnabled() {
        VerificationCodeDto codeContainer = new VerificationCodeDto("123399");
        given()
                .noAuth()
                .body(codeContainer)
                .when()
                .post("api/users/1/verify")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("title", equalTo("BAD_REQUEST"))
                .body("status", equalTo(400))
                .body("detail", equalTo("User is already enabled"))
                .body("timestamp", notNullValue())
                .body("dev_message", endsWith("BadRequestException"));

        assertThat(new Request(dataSource,
                "SELECT enabled FROM users WHERE user_id=1"))
                .hasNumberOfRows(1)
                .row(0)
                .value("enabled").isEqualTo(true);
    }
}
