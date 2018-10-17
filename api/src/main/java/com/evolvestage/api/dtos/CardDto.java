package com.evolvestage.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDto {
    private Integer id;
    private String content;
    private String title;
    private Integer order;
    private List<UserBriefDto> users;
    private List<LabelDto> labels;
}
