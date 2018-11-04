package com.evolvestage.api.dtos;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenContainer {
    private String token;
    private Integer userId;

    public TokenContainer(String token) {
        this.token = token;
    }
}
