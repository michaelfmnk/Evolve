package com.dreamteam.api.services;

import com.dreamteam.api.dtos.BoardColumnDto;
import com.dreamteam.api.entities.BoardColumn;
import com.dreamteam.api.repositories.ColumnsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardColumnService {

    private final ConverterService converter;
    private final ColumnsRepository columnsRepository;

    public BoardColumnDto createColumn(BoardColumnDto boardColumnDto) {
        BoardColumn boardColumnEntity = converter.toEntity(boardColumnDto);
        boardColumnEntity = columnsRepository.save(boardColumnEntity);
        return converter.toDto(boardColumnEntity);
    }
}
