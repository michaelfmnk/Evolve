package com.evolvestage.api.activities.events;

import com.evolvestage.api.entities.Activity;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BoardCreatedEvent implements ActivityEvent {
    private Integer userId;
    private Integer boardId;
    private String boardName;

    @Builder
    public BoardCreatedEvent(Integer userId, Integer boardId, String boardName) {
        this.userId = userId;
        this.boardId = boardId;
        this.boardName = boardName;
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
        return Activity.ActivityType.BOARD_CREATED;
    }

    @Override
    public Map<String, Object> getData() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("board_name", boardName);
        return data;
    }
}
