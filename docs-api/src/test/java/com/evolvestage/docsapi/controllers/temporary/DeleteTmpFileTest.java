package com.evolvestage.docsapi.controllers.temporary;

import com.evolvestage.docsapi.BaseTest;
import org.assertj.db.type.Request;
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

public class DeleteTmpFileTest extends BaseTest {

    @Test
    public void shouldFailDeleteOnNotFound() {
        given()
                .when()
                .header(RIGHT_HEADER)
                .delete("/docs-api/temporary" + UUID.randomUUID())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void deleteTmpFile() throws IOException {
        String fileId = "00000000-0000-0000-0000-000000000002";
        File file = new File(temporaryStorage, fileId);
        Files.copy(new File(testDir, "empty-sound.mp3").toPath(), file.toPath());
        assertTrue(file.exists());

        given()
                .when()
                .header(RIGHT_HEADER)
                .delete("/docs-api/temporary/" + fileId)
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        assertFalse(file.exists());
        assertThat(new Request(dataSource, String.format("SELECT count(1) FROM documents WHERE file_id='%s'", fileId)))
                .row().value().isEqualTo(0);
    }

}
