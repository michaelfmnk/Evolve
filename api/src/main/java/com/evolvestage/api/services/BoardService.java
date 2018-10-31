package com.evolvestage.api.services;

import com.evolvestage.api.docs.DocsApiService;
import com.evolvestage.api.dtos.BoardDto;
import com.evolvestage.api.entities.Board;
import com.evolvestage.api.repositories.BoardsRepository;
import com.evolvestage.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@AllArgsConstructor
public class BoardService {

    private final ConverterService converter;
    private final BoardsRepository boardsRepository;
    private final CommonBackgroundService backgroundService;
    private final MessagesService messagesService;
    private final DocsApiService docsApiService;

    @Transactional
    public BoardDto createBoard(BoardDto boardDto) {
        Board boardEntity = converter.toEntity(boardDto);
        boardEntity = boardsRepository.save(boardEntity);
        if (Objects.nonNull(boardDto.getBackgroundId())
                && !backgroundService.isDefaultBackground(boardDto.getBackgroundId())) {
            docsApiService.moveToPermanentStorage(boardDto.getBackgroundId(), boardEntity.getBoardId(), true);
        }
        return converter.toDto(boardEntity);
    }

    public BoardDto getBoardById(Integer boardId) {
        Board boardEntity = findValidBoard(boardId);
        return converter.toDto(boardEntity);
    }

    @Transactional
    public void deleteBoard(Integer boardId) {
        Board boardEntity = findValidBoard(boardId);
        if (Objects.nonNull(boardEntity.getBackgroundId())
                && !backgroundService.isDefaultBackground(boardEntity.getBackgroundId())) {
            docsApiService.deleteDocument(boardEntity.getBackgroundId());
        }
        boardsRepository.delete(boardEntity);
    }

    private Board findValidBoard(Integer boardId) {
       return boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("board.not.found")));
    }
}
