package com.evolvestage.docsapi.controllers.permanent;

import com.evolvestage.docsapi.BaseTest;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.testcontainers.shaded.com.google.common.collect.Sets;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

public class GetFilesTest extends BaseTest {
    @Test
    public void shouldGetFiles() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .header(RIGHT_HEADER)
                .param("file_id", Sets.newHashSet(
                        "00000000-0000-0000-0000-000000000001", "00000000-0000-0000-0000-000000000002"))
                .get("/docs-api/permanent")
                .then().extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("[0].file_id", equalTo("00000000-0000-0000-0000-000000000001"))
                .body("[0].data_id", equalTo(12))
                .body("[0].size", notNullValue())
                .body("[0].document_name", equalTo("empty-pdf.pdf"))
                .body("[0].mime", equalTo("application/pdf"))
                .body("[1].file_id", equalTo("00000000-0000-0000-0000-000000000002"))
                .body("[1].data_id", nullValue())
                .body("[1].size", notNullValue())
                .body("[1].document_name", equalTo("empty-pdf-2.pdf"))
                .body("[1].mime", equalTo("application/pdf"));

    }

    @Test
    public void shouldNotGetFilesOnNotFound() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .header(RIGHT_HEADER)
                .param("file_id", Sets.newHashSet(
                        "00000000-0000-0000-0000-000000000333", "00000000-0000-0000-0000-000000000002"))
                .get("/docs-api/permanent")
                .then().extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("title", Matchers.equalTo("NOT_FOUND"))
                .body("status", Matchers.equalTo(404))
                .body("detail", Matchers.equalTo("No documents were found"))
                .body("timestamp", Matchers.notNullValue())
                .body("dev_message", Matchers.equalTo("javax.persistence.EntityNotFoundException"));
    }

    @Test
    public void shouldNotGetFilesOnUnauthorized() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .header(WRONG_HEADER)
                .param("file_id", Sets.newHashSet("00000000-0000-0000-0000-000000000002"))
                .get("/docs-api/permanent")
                .then().extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}
