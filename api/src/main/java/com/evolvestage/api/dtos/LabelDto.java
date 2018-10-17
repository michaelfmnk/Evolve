package com.evolvestage.api.dtos;

import com.evolvestage.api.validation.ValidHexColor;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LabelDto {
    private Integer id;
    private String name;
    @ValidHexColor
    private String color;
    private Integer boardId;
}
