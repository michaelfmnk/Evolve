package com.evolvestage.api.listeners.events;

import com.evolvestage.api.entities.Activity.ActivityType;

import java.util.Map;

public interface Event {
    Integer getUserId();
    Integer getBoardId();
    ActivityType getType();
    Map<String, Object> getData();
}
