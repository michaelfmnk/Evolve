package com.evolvestage.docsapi.services;

import com.evolvestage.docsapi.dtos.DocumentDto;
import com.evolvestage.docsapi.entities.Document;
import com.evolvestage.docsapi.utils.IfNullReturnNull;
import org.springframework.stereotype.Service;

@Service
public class ConverterService {
    @IfNullReturnNull
    public DocumentDto toDto(Document entity) {
        return DocumentDto.builder()
                .fileId(entity.getFileId())
                .dataId(entity.getDataId())
                .size(entity.getSize())
                .mime(entity.getMime())
                .documentName(entity.getDocumentName())
                .build();
    }

}
