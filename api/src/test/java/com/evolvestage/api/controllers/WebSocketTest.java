package com.evolvestage.api.controllers;


import com.evolvestage.api.BaseTest;
import com.evolvestage.api.security.JwtUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.extern.apachecommons.CommonsLog;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.testcontainers.shaded.org.apache.http.HttpStatus;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.evolvestage.api.config.ws.WebSocketConfig.WS_URL;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@CommonsLog
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class WebSocketTest extends BaseTest {

    private WebSocketStompClient stompClient;
    private WebSocketHttpHeaders wsHeaders;

    private DefaultStompFrameHandler stompFrameHandler;
    private LinkedBlockingDeque<String> blockingQueue;

    @BeforeEach
    public void before() {
        blockingQueue = new LinkedBlockingDeque<>();
        stompClient = new WebSocketStompClient(
                new SockJsClient(Collections.singletonList(new WebSocketTransport(new StandardWebSocketClient())))
        );
        stompFrameHandler = new DefaultStompFrameHandler();

        wsHeaders = new WebSocketHttpHeaders();
        final String userToken = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(1)
                        .email("michaelfmnk@gmail.com")
                        .build());
        wsHeaders.put(authProperties.getHeaderName(), Collections.singletonList(userToken));
    }

    @Test
    @SneakyThrows
    public void shouldSendMessageToSocketOnCreateBoard() {
        StompSession session = connect(wsHeaders);
        StompSession.Subscription subscription = session.subscribe("/boards/1", stompFrameHandler);
        given()
                .auth()
                .when()
                .patch("/api/boards/1/columns/1/cards/1/archive")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        String message = blockingQueue.poll(3000, MILLISECONDS);
        Map<String, String> res = getJsonMap(message);
        Assert.assertThat(res.get("user_id"), is(1.0));
        Assert.assertThat(res.get("board_id"), is(1.0));
        Assert.assertThat(res.get("type"), is("CARD_ARCHIVED"));
        Assert.assertNotNull(res.get("data"));
        subscription.unsubscribe();
    }

    @Test
    public void notAuthorizedUserShouldNotHaveAbilityToConnect() {
        ExecutionException exception = assertThrows(ExecutionException.class, () -> {
            connect(null);

            stompClient
                    .connect(String.format("ws://localhost:%s%s", port, WS_URL), new StompSessionHandlerAdapter() {})
                    .get(100, MILLISECONDS);
        });
        assertThat(exception.getCause(), hasProperty("statusCode"));
        assertThat(exception.getCause(), hasProperty("statusCode", is(UNAUTHORIZED)));
    }

    @Test
    public void shouldNotAllowWrongUserToSubscribe() throws InterruptedException, ExecutionException, TimeoutException {
        wsHeaders = new WebSocketHttpHeaders();
        final String userToken = jwtTokenUtil.generateToken(
                JwtUser.builder()
                        .id(3)
                        .email("admin@gmail.com")
                        .build());
        wsHeaders.put(authProperties.getHeaderName(), Collections.singletonList(userToken));
        StompSession session = connect(wsHeaders);
        StompSession.Subscription subscription = session.subscribe("/boards/1", stompFrameHandler);

        given()
                .auth()
                .when()
                .patch("/api/boards/1/columns/1/cards/1/archive")
                .then()
                .extract().response().prettyPeek()
                .then()
                .statusCode(HttpStatus.SC_OK);

        String message = blockingQueue.poll(3000, MILLISECONDS);
        Assert.assertNull(message);
        subscription.unsubscribe();
    }

    @SneakyThrows
    private StompSession connect(WebSocketHttpHeaders headers) {
        return stompClient
                .connect(String.format("ws://localhost:%s%s", port, WS_URL), headers,
                        new StompSessionHandlerAdapter() {})
                .get(5, TimeUnit.SECONDS);

    }

    @Nullable
    private Map<String, String> getJsonMap(String message) {
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        return new Gson().fromJson(message, type);
    }

    class DefaultStompFrameHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return byte[].class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            log.info("handle frame " + new String((byte[]) o));
            blockingQueue.offer(new String((byte[]) o));
        }
    }
}
