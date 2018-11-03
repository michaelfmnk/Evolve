package com.evolvestage.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBriefDto {
    private Integer id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private UUID avatarId;
    private String avatarUrl;
}
