package com.dreamteam.api.services;

import com.dreamteam.api.dtos.BoardBriefDto;
import com.dreamteam.api.dtos.BoardDto;
import com.dreamteam.api.dtos.UserBriefDto;
import com.dreamteam.api.dtos.UserDto;
import com.dreamteam.api.dtos.BoardColumnDto;
import com.dreamteam.api.entities.Board;
import com.dreamteam.api.entities.User;
import com.dreamteam.api.entities.BoardColumn;
import com.dreamteam.api.repositories.UsersRepository;
import com.dreamteam.api.repositories.BoardsRepository;
import com.dreamteam.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Service
@AllArgsConstructor
public class ConverterService {

    private UsersRepository usersRepository;
    private BoardsRepository boardsRepository;
    private MessagesService messagesService;

    public UserDto toDto(User entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return UserDto.builder()
                .id(entity.getUserId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .ownBoards(emptyIfNull(entity.getOwnBoards()).stream()
                        .map(this::toBriefDto)
                        .collect(Collectors.toList()))
                .joinedBoards(emptyIfNull(entity.getJoinedBoards()).stream()
                        .map(this::toBriefDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public UserBriefDto toBriefDto(User entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return UserBriefDto.builder()
                .id(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .avatarId(entity.getAvatarId())
                .build();
    }


    public BoardDto toDto(Board entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return BoardDto.builder()
                .id(entity.getBoardId())
                .name(entity.getName())
                .ownerId(entity.getOwner().getUserId())
                .backgroundId(entity.getBackgroundId())
                .build();
    }

    public BoardBriefDto toBriefDto(Board entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return BoardBriefDto.builder()
                .id(entity.getBoardId())
                .name(entity.getName())
                .ownerId(entity.getOwner().getUserId())
                .backgroundId(entity.getBackgroundId())
                .build();
    }

    public Board toEntity(BoardDto dto) {
        User owner = usersRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("user.not.found")));
        return Board.builder()
                .name(dto.getName())
                .owner(owner)
                .backgroundId(dto.getBackgroundId())
                .build();
    }

    public BoardColumnDto toDto(BoardColumn entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return BoardColumnDto.builder()
                .id(entity.getColumnId())
                .name(entity.getName())
                .order(entity.getOrder())
                .boardId(entity.getBoard().getBoardId())
                .build();
    }

    public BoardColumn toEntity(BoardColumnDto dto) {
        Board board = boardsRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("board.not.found")));
        return BoardColumn.builder()
                .name(dto.getName())
                .order(dto.getOrder())
                .board(board)
                .build();
    }
}
