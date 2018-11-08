package com.evolvestage.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardBriefDto {
    private Integer id;
    private String content;
    @NotBlank
    private String title;
    private Integer columnId;
    private Integer authorId;
    private Integer order;
}
