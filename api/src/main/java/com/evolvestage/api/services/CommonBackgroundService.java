package com.evolvestage.api.services;

import com.evolvestage.api.dtos.CommonBackgroundDto;
import com.evolvestage.api.dtos.Pagination;
import com.evolvestage.api.entities.CommonBackground;
import com.evolvestage.api.repositories.CommonBackgroundRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonBackgroundService {

    private final CommonBackgroundRepository backgroundRepository;
    private final ConverterService converterService;

    public Pagination<CommonBackgroundDto> getCommonBackground(Pageable pageable) {
        Page<CommonBackground> page = backgroundRepository.findAll(pageable);

        return Pagination.<CommonBackgroundDto>builder()
                .data(page.stream()
                        .map(converterService::toDto)
                        .collect(Collectors.toList()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    public boolean isDefaultBackground(UUID fileId) {
        return backgroundRepository.existsById(fileId);
    }
}
