package com.evolvestage.docsapi.controllers.permanent;

import com.evolvestage.docsapi.BaseTest;
import org.assertj.db.type.Request;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeleteFilesTest extends BaseTest {

    private File pdf;
    private File mp3;

    private static final String PDF_ID = "00000000-0000-0000-0000-000000000001";
    private static final String MP3_ID = "00000000-0000-0000-0000-000000000002";

    @Before
    public void before() throws IOException {
        pdf = new File(permanentStorage, PDF_ID);
        mp3 = new File(permanentStorage, MP3_ID);
        Files.copy(new File(testDir, "empty-pdf.pdf").toPath(), pdf.toPath());
        Files.copy(new File(testDir, "empty-sound.mp3").toPath(), mp3.toPath());
        assertTrue(pdf.exists());
    }

    @Test
    public void deleteFiles() {
        given()
                .param("file_id", "00000000-0000-0000-0000-000000000001",
                        "00000000-0000-0000-0000-000000000002")
                .when()
                .header(RIGHT_HEADER)
                .delete("/docs-api/permanent")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertFalse(mp3.exists());
        assertFalse(pdf.exists());
        assertThat(new Request(dataSource, "SELECT count(1) " +
                "FROM documents WHERE file_id IN " +
                "('00000000-0000-0000-0000-000000000001', '00000000-0000-0000-0000-000000000002')"))
                .row().value().isEqualTo(0);
    }

    @Test
    public void shouldFailDeleteFileOnNotFound() {
        given()
                .param("file_id", UUID.randomUUID().toString())
                .when()
                .header(RIGHT_HEADER)
                .delete("/docs-api/permanent")
                .then().extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("title", Matchers.equalTo("NOT_FOUND"))
                .body("status", Matchers.equalTo(404))
                .body("detail", Matchers.equalTo("No documents were found"))
                .body("timestamp", Matchers.notNullValue())
                .body("dev_message", Matchers.endsWith("EntityNotFoundException"));
    }

    @Test
    public void shouldFailDeleteFileOnUnauthorized() {
        given()
                .param("file_id", UUID.randomUUID().toString())
                .when()
                .header(WRONG_HEADER)
                .delete("/docs-api/permanent")
                .then().extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }


}
