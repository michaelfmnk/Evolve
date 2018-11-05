package com.evolvestage.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardDto {
    private Integer id;
    private String content;
    @NotBlank
    private String title;
    private Integer columnId;
    private Integer authorId;
    private Integer order;
    private List<UserBriefDto> users;
    private List<LabelDto> labels;
}
