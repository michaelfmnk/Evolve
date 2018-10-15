package com.evolvestage.docsapi.controllers.temporary;

import com.evolvestage.docsapi.BaseTest;
import com.evolvestage.docsapi.dtos.DocumentDto;
import org.assertj.db.type.Request;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.assertj.db.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;

public class UploadFileTest extends BaseTest {

    @Test
    public void shouldUploadFile() throws IOException {
        File file = new File(testDir, "empty-sound.mp3");
        String json = given()
                .multiPart("file", file)
                .when()
                .header(RIGHT_HEADER)
                .post("/docs-api/temporary")
                .then()
                .extract().response().prettyPeek().then()
                .statusCode(HttpStatus.SC_OK)
                .body("file_id", notNullValue())
                .body("data_id", isEmptyOrNullString())
                .body("document_name", equalTo("empty-sound"))
                .body("mime", equalTo("audio/mpeg")).extract().response().asString();
        DocumentDto responseDto = objectMapper.readValue(json, DocumentDto.class);
        assertTrue(new File(temporaryStorage, responseDto.getFileId().toString()).exists());

        assertThat(new Request(dataSource, String.format("SELECT count(1) FROM documents WHERE file_id='%s'",
                responseDto.getFileId())))
                .row().value().isEqualTo(1);
    }

    @Test
    public void shouldFailUploadWithLongNameFile() throws IOException {
        BDDMockito.when(storageProperties.getMaxNameLength()).thenReturn(1);
        File file = new File(testDir, "empty-sound.mp3");
        given()
                .multiPart("file", file)
                .when()
                .header(RIGHT_HEADER)
                .post("/docs-api/temporary")
                .then()
                .extract().response().prettyPeek().then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("title", Matchers.equalTo("BAD_REQUEST"))
                .body("status", Matchers.equalTo(400))
                .body("detail", Matchers.equalTo("file name is too long"))
                .body("timestamp", Matchers.notNullValue())
                .body("dev_message", Matchers.endsWith("BadRequestException"));
        BDDMockito.when(storageProperties.getMaxNameLength()).thenReturn(100);
    }

    @Test
    public void shouldFailUploadOnUnauthorized() throws IOException {
        File file = new File(testDir, "empty-sound.mp3");
        given()
                .multiPart("file", file)
                .when()
                .header(WRONG_HEADER)
                .post("/docs-api/temporary")
                .then()
                .extract().response().prettyPeek().then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

}
