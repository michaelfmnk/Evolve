package com.evolvestage.api.services;

import com.evolvestage.api.dtos.ActivityDto;
import com.evolvestage.api.dtos.Pagination;
import com.evolvestage.api.entities.Activity;
import com.evolvestage.api.repositories.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final ConverterService converterService;

    public Pagination<ActivityDto> getBoardActivity(Integer boardId, Pageable pageable) {
        Page<Activity> page = activityRepository.findByBoardId(boardId, pageable);

        return Pagination.<ActivityDto>builder()
                .data(page.getContent().stream()
                        .map(converterService::toDto)
                        .collect(Collectors.toList()))
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
