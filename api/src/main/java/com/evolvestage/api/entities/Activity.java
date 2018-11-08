package com.evolvestage.api.entities;

import com.evolvestage.api.utils.JsonDataType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activities")
@TypeDef(name = "JsonDataType", typeClass = JsonDataType.class)
public class Activity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;

    private Integer boardId;
    private Integer actorId;

    @CreationTimestamp
    private LocalDateTime recordedDate;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Type(type = "JsonDataType")
    private Map<String, Object> data;

    public enum ActivityType {
        BOARD_CREATED,
        COLUMN_CREATED,
        CARD_CREATED,
        CARD_ARCHIVED,
        CARD_MOVED,
        USER_ADDED_TO_CARD,
        USER_ADDED_TO_BOARD
    }
}
