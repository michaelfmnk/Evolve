package com.evolvestage.api.config.ws;

import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

@UtilityClass
public class WebSocketChannels {
    private final String BOARD_CHANNEL_TEMPLATE = "/boards/";

    public String getBoardChannel(Integer boardId) {
        return BOARD_CHANNEL_TEMPLATE + boardId;
    }

    public Integer getBoardIdFromChannelName(String channel) {
        try {
            return Integer.valueOf(channel.replace(BOARD_CHANNEL_TEMPLATE, Strings.EMPTY));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
