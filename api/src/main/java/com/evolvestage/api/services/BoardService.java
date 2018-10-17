package com.evolvestage.api.services;

import com.evolvestage.api.dtos.BoardDto;
import com.evolvestage.api.entities.Board;
import com.evolvestage.api.repositories.BoardsRepository;
import com.evolvestage.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class BoardService {

    private final ConverterService converter;
    private final BoardsRepository boardsRepository;
    private final MessagesService messagesService;

    public BoardDto createBoard(BoardDto boardDto) {
        Board boardEntity = converter.toEntity(boardDto);
        boardEntity = boardsRepository.save(boardEntity);
        return converter.toDto(boardEntity);
    }

    public BoardDto getBoardById(Integer boardId) {
        Board boardEntity = boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("board.not.found")));
        return converter.toDto(boardEntity);
    }
}
