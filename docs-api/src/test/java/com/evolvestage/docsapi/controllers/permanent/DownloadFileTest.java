package com.evolvestage.docsapi.controllers.permanent;

import com.evolvestage.docsapi.BaseTest;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.*;

public class DownloadFileTest extends BaseTest {

    private File pdf;

    private static final String PDF_ID = "00000000-0000-0000-0000-000000000001";

    @Before
    public void before() throws IOException {
        pdf = new File(permanentStorage, PDF_ID);
        Files.copy(new File(testDir, "empty-pdf.pdf").toPath(), pdf.toPath());
        assertTrue(pdf.exists());
    }

    @Test
    public void shouldDownloadFile() throws IOException {
        byte[] bytesFile = Files.readAllBytes(pdf.toPath());

        given()
                .header(RIGHT_HEADER)
                .when()
                .get("/docs-api/permanent/00000000-0000-0000-0000-000000000001")
                .then()
                .extract().response().prettyPeek().then()
                .statusCode(HttpStatus.SC_OK)
                .body(is(new String(bytesFile)));

    }

    @Test
    public void shouldFailDownloadFileOnUnauthorized() throws IOException {
        given()
                .header(WRONG_HEADER)
                .when()
                .get("/docs-api/permanent/00000000-0000-0000-0000-000000000001")
                .then()
                .extract().response().prettyPeek().then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

    @Test
    public void shouldNotFindFile() {
        given()
                .header(RIGHT_HEADER)
                .when()
                .get("/docs-api/permanent/" + UUID.randomUUID().toString())
                .then()
                .extract().response().prettyPeek().then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("title", equalTo("NOT_FOUND"))
                .body("status", equalTo(404))
                .body("detail", equalTo("Document not found"))
                .body("timestamp", notNullValue())
                .body("dev_message", endsWith("EntityNotFoundException"));
    }
}
