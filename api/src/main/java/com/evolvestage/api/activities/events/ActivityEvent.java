package com.evolvestage.api.activities.events;

import com.evolvestage.api.entities.Activity.ActivityType;

import java.util.Map;

public interface ActivityEvent {
    Integer getUserId();
    Integer getBoardId();
    ActivityType getType();
    Map<String, Object> getData();
}
