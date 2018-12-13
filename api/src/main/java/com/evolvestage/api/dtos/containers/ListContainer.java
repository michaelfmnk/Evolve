package com.evolvestage.api.dtos.containers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListContainer<T> {
    @NotEmpty
    private List<T> data;
}
