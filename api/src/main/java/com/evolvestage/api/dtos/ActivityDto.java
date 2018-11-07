package com.evolvestage.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    private Integer activityId;
    private UserBriefDto actor;
    private Integer boardId;
    private LocalDateTime recordedDate;
    private Map<String, Object> data;
}
