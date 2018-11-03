package com.evolvestage.api.services;

import com.evolvestage.api.dtos.CommonBackgroundDto;
import com.evolvestage.api.repositories.CommonBackgroundRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonBackgroundService {

    private final CommonBackgroundRepository backgroundRepository;
    private final ConverterService converterService;

    public List<CommonBackgroundDto> getCommonBackground(Pageable pageable) {
        return backgroundRepository.findAll(pageable).stream()
                .map(converterService::toDto)
                .collect(Collectors.toList());
    }

    public boolean isDefaultBackground(UUID fileId) {
        return backgroundRepository.existsById(fileId);
    }
}
