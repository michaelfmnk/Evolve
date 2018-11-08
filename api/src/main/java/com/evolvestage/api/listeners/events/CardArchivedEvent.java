package com.evolvestage.api.listeners.events;

import com.evolvestage.api.entities.Activity;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;

public class CardArchivedEvent implements Event {
    private Integer userId;
    private Integer boardId;
    private Integer cardId;
    private String cardTitle;

    @Builder
    public CardArchivedEvent(Integer userId, Integer boardId, Integer cardId, String cardTitle) {
        this.userId = userId;
        this.boardId = boardId;
        this.cardId = cardId;
        this.cardTitle = cardTitle;
    }

    @Override
    public Integer getUserId() {
        return userId;
    }

    @Override
    public Integer getBoardId() {
        return boardId;
    }

    @Override
    public Activity.ActivityType getType() {
        return Activity.ActivityType.CARD_ARCHIVED;
    }

    @Override
    public Map<String, Object> getData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("card_id", cardId);
        data.put("card_title", cardTitle);
        return data;
    }
}
