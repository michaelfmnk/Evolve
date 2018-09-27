package com.dreamteam.api.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TimeProvider {
    private TimeProvider() {}

    public Date getDate() {
        return new Date();
    }

    public LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }
}
