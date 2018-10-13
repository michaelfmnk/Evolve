package com.dreamteam.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabelDto {
    private Integer labelId;
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(regexp="#[0-9a-fA-F]+")
    private String color;
    private Integer boardId;
}
