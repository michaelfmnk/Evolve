package com.evolvestage.api.services;

import com.evolvestage.api.dtos.BoardColumnDto;
import com.evolvestage.api.entities.BoardColumn;
import com.evolvestage.api.repositories.ColumnsRepository;
import com.evolvestage.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class BoardColumnService {

    private final MessagesService messagesService;
    private final ConverterService converter;
    private final ColumnsRepository columnsRepository;

    public BoardColumnDto createColumn(BoardColumnDto boardColumnDto) {
        BoardColumn boardColumnEntity = converter.toEntity(boardColumnDto);
        boardColumnEntity = columnsRepository.save(boardColumnEntity);
        return converter.toDto(boardColumnEntity);
    }

    @Transactional
    public void deleteColumn(Integer boardId, Integer columnId) {
        if (columnsRepository.existsColumnByColumnIdAndBoardId(boardId, columnId)) {
            columnsRepository.deleteColumnByColumnIdAndBoardId(boardId, columnId);
        } else  {
            throw new EntityNotFoundException(messagesService.getMessage("column.not.found"));
        }
    }

    public BoardColumn findValidByColumnIdAndBoardId(Integer boardId, Integer columnId) {
        return columnsRepository.findColumnByColumnIdAndBoardId(boardId, columnId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("column.not.found")));

    }

    public BoardColumnDto updateColumn(Integer boardId, Integer columnId, BoardColumnDto columnDto) {
        BoardColumn column = findValidByColumnIdAndBoardId(boardId, columnId);
        column.setName(columnDto.getName());
        column = columnsRepository.save(column);
        return converter.toBriefDto(column);
    }
}
