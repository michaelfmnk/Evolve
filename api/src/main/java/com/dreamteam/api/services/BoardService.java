package com.dreamteam.api.services;

import com.dreamteam.api.dtos.BoardDto;
import com.dreamteam.api.entities.Board;
import com.dreamteam.api.repositories.BoardsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {

    private final ConverterService converter;
    private final BoardsRepository boardsRepository;

    public BoardDto createBoard(BoardDto boardDto) {
        Board boardEntity = converter.toEntity(boardDto);
        boardEntity = boardsRepository.save(boardEntity);
        return converter.toDto(boardEntity);
    }
}
