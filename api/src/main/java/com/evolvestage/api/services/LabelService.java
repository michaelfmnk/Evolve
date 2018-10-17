package com.evolvestage.api.services;

import com.evolvestage.api.dtos.LabelDto;
import com.evolvestage.api.entities.Label;
import com.evolvestage.api.repositories.LabelsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LabelService {

    private final ConverterService converter;
    private final LabelsRepository labelsRepository;

    public LabelDto createLabel(LabelDto labelDto) {
        Label labelEntity = converter.toEntity(labelDto);
        labelEntity = labelsRepository.save(labelEntity);
        return converter.toDto(labelEntity);
    }
}
