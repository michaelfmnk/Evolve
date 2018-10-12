package com.dreamteam.api.services;

import com.dreamteam.api.dtos.LabelDto;
import com.dreamteam.api.entities.Label;
import com.dreamteam.api.repositories.LabelsRepository;
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
