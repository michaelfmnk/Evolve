package com.evolvestage.api.services;

import com.evolvestage.api.docs.DocsApiService;
import com.evolvestage.api.dtos.BoardBriefDto;
import com.evolvestage.api.dtos.BoardDto;
import com.evolvestage.api.entities.Board;
import com.evolvestage.api.repositories.BoardsRepository;
import com.evolvestage.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;

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
        saveBackground(boardEntity.getBackgroundId(), boardEntity.getBoardId());
        return converter.toDto(boardEntity);
    }

    @Transactional
    public void deleteBoard(Integer boardId) {
        Board boardEntity = findValidBoard(boardId);
        deleteBackgroundIfNotDefault(boardEntity.getBackgroundId());
        boardsRepository.delete(boardEntity);
    }

    @Transactional
    public BoardBriefDto updateBoard(Integer boardId, BoardBriefDto boardDto) {
        Board board = findValidBoard(boardId);
        board.setName(boardDto.getName());
        // if file was changed delete old, save new background
        if (!Objects.equals(board.getBackgroundId(), boardDto.getBackgroundId())) {
            UUID oldBackground = board.getBackgroundId();
            board.setBackgroundId(boardDto.getBackgroundId());
            saveBackground(board.getBackgroundId(), board.getBoardId());
            deleteBackgroundIfNotDefault(oldBackground);
        }
        board = boardsRepository.save(board);
        return converter.toBriefDto(board);
    }

    public BoardDto getBoardById(Integer boardId) {
        Board boardEntity = findValidBoard(boardId);
        return converter.toDto(boardEntity);
    }

    private Board findValidBoard(Integer boardId) {
        return boardsRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("board.not.found")));
    }

    private void deleteBackgroundIfNotDefault(UUID backgroundId) {
        if (Objects.nonNull(backgroundId)
                && !backgroundService.isDefaultBackground(backgroundId)) {
            docsApiService.deleteDocument(backgroundId);
        }
    }

    private void saveBackground(UUID backgroundId, Integer boardId) {
        if (Objects.nonNull(backgroundId)
                && !backgroundService.isDefaultBackground(backgroundId)) {
            docsApiService.moveToPermanentStorage(backgroundId, boardId, true);
        }
    }
}
