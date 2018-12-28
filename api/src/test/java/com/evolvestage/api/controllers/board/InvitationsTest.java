package com.evolvestage.api.controllers.board;

import com.evolvestage.api.BaseTest;
import com.evolvestage.api.dtos.VerificationCodeDto;
import com.evolvestage.api.dtos.containers.EmailsContainer;
import com.evolvestage.api.dtos.containers.ListContainer;
import com.evolvestage.api.entities.BoardInvitation;
import com.mailjet.client.MailjetRequest;
import lombok.SneakyThrows;
import org.assertj.db.type.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.db.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class InvitationsTest extends BaseTest {

    @BeforeEach
    public void before() {
        redisTemplate.delete("invitations");
    }

    @Test
    @SneakyThrows
    public void shouldInvitePeople() {
        List<String> data = Arrays.asList("someoneelse@gmail.com", "test@test.com");
        EmailsContainer dto = new EmailsContainer(data);
        given()
                .auth()
                .body(dto)
                .when()
                .post("/api/boards/1/collaborators")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);
        verify(mailjetClient, times(2)).post(any(MailjetRequest.class));
        Long count = redisTemplate.opsForHash().size("invitations");
        assertEquals(2L, (long) count);
    }

    @Test
    @SneakyThrows
    public void shouldGet422() {
        List<String> data = Arrays.asList("someoneelse@gmail.com", "testtest.com");
        ListContainer<String> dto = new ListContainer<>(data);
        given()
                .auth()
                .body(dto)
                .when()
                .post("/api/boards/1/collaborators")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
    }


    @Test
    @SneakyThrows
    public void shouldAcceptInvitation() {
        BoardInvitation invitation = BoardInvitation.builder()
                .boardId(2)
                .email("michaelfmnk@gmail.com")
                .build();
        String code = UUID.randomUUID().toString();
        redisTemplate.opsForHash().put("invitations", code, objectMapper.writeValueAsString(invitation));

        VerificationCodeDto container = new VerificationCodeDto(code);
        given()
                .auth()
                .body(container)
                .when()
                .post("/api/invitations")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        assertThat(new Request(dataSource, "SELECT * FROM boards_users WHERE user_id=1 AND board_id=2"))
                .hasNumberOfRows(1);
        Long count = redisTemplate.opsForHash().size("invitations");
        assertEquals(0L, (long) count);
    }


}
