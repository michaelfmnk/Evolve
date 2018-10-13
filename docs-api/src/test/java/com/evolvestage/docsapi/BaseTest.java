package com.evolvestage.docsapi;

import com.evolvestage.docsapi.properties.AuthProperties;
import com.evolvestage.docsapi.properties.StorageProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

@SqlGroup({@Sql(value = {"classpath:test-clean.sql"}), @Sql})
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {
    @LocalServerPort
    protected Integer port;
    @MockBean
    protected StorageProperties storageProperties;
    @Autowired
    protected AuthProperties authProperties;
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected DataSource dataSource;

    private static final String PERMANENT = "permanent";
    private static final String TEMPORARY = "temporary";

    protected Header RIGHT_HEADER;
    protected Header WRONG_HEADER;

    protected String testDir =
            new File(getClass().getClassLoader().getResource("files").getPath()).getAbsolutePath();
    protected String permanentStorage = new File(testDir, PERMANENT).getAbsolutePath();
    protected String temporaryStorage = new File(testDir, TEMPORARY).getAbsolutePath();

    @Before
    public void setup() throws IOException {
        RestAssured.port = port;

        BDDMockito.given(storageProperties.getTemporaryLocation()).willReturn(temporaryStorage);
        BDDMockito.given(storageProperties.getPermanentLocation()).willReturn(permanentStorage);
        BDDMockito.given(storageProperties.getMaxNameLength()).willReturn(100);

        Files.createDirectory(new File(temporaryStorage).toPath());
        Files.createDirectories(new File(permanentStorage).toPath());

        RIGHT_HEADER = new Header(authProperties.getHeaderName(), "eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjQ2OTAyNjczN" +
                "zEsImlhdCI6MTUzNDU5Mzc3MSwianRpIjoxfQ.2OpgNnAQTfNHvX_RfKGmMEgzUSgYAZAySDw6yAtjUk58DtSXoEkF6PjAeHgy" +
                "WncIgFCqkThuwKzgg9ItFxYKtC-cxNgmwCSy088fl_93QdBGsqf9X7DEPJ1yULckoyyLUJ3veEaN3q23t4v2_MvrvEBoR7XSQxV" +
                "f4ftgKuB0fbIzj5U6xtt2hjBh-P2wlZ2OaSVPW4pkiOkGuFhSAZ0Qt1IvKqx1e8DVhw73-MssYHNiH8NjEkF59n4TQlYsbY8I9O7" +
                "eohfsGa5ushUtD_4oX5LaVZvB3ZgG1afJviNSZnd67uGO32qOliRuxGY_Ot7SRTJLLACaj5gXy9XD7ymv3w"); //expires in 100years

        WRONG_HEADER = new Header(authProperties.getHeaderName(), "eyJhbGciOiJSUzI1NiJ9.eyJleHAiOjQ2OTAyNjczN" +
                "zEsImlhdCI6MTUzNDU5Mzc3MSwianRpIjoxfQ.2OpgNnAQTfNHvX_RfKGmMEgzUSgYAZAySDw6yAtjUk58DtSXoEkF6PjAeHgy" +
                "WncIgFCqkThuwKzgg9ItFxYKtC-cxNgmwCSy088fl_93QdBGsqf9X7DEPJ1yULckoyyLUJ3veEaN3q23t4v2_MvrvEBoR7XSQxV" +
                "f4ftgKuB0fbIzj5U6xtt2hjBh-rrt678OaSVPW4pkiOkGuFhSAZ0Qt1IvKqx1e8DVhw73-MssYHNiH8NjEkF59n4TQlYsbY8I9O7" +
                "eohfsGa5ushUtD_4oX5LaVZvB3ZgG1afJviNSZnd67uGO32qOliRuxGY_Ot7SRTJLLACaj5gXy9XD7ymv3w"); //expires in 100years
    }

    @After
    public void after() throws IOException {
        FileUtils.deleteDirectory(new File(temporaryStorage));
        FileUtils.deleteDirectory(new File(permanentStorage));
    }

}
