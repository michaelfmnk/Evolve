package com.evolvestage.docsapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MoveDocumentDto {
    private UUID fileId;
    private Integer dataId;
    private Boolean isPublic;
}
