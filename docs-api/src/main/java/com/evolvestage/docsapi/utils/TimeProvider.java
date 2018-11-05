package com.evolvestage.docsapi.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TimeProvider {
    private TimeProvider() {
    }

    public Date getDate() {
        return new Date();
    }

    public LocalDateTime getLDT() {
        return LocalDateTime.now();
    }
}
