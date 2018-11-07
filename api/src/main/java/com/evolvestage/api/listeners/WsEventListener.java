package com.evolvestage.api.listeners;

import com.evolvestage.api.config.ws.WebSocketChannels;
import com.evolvestage.api.listeners.events.Event;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class WsEventListener {
    private final SimpMessagingTemplate webSocket;

    @Async
    @EventListener(value = Event.class)
    public void onEvent(Event event) {
        webSocket.convertAndSend(WebSocketChannels.getBoardChannel(event.getBoardId()), event);
    }
}
