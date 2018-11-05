package com.evolvestage.api.services;

import com.evolvestage.api.dtos.*;
import com.evolvestage.api.entities.*;
import com.evolvestage.api.repositories.BoardsRepository;
import com.evolvestage.api.repositories.ColumnsRepository;
import com.evolvestage.api.repositories.UsersRepository;
import com.evolvestage.api.utils.MessagesService;
import com.evolvestage.api.utils.UrlUtils;
import lombok.AllArgsConstructor;
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
    private ColumnsRepository columnsRepository;
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
                .avatarUrl(UrlUtils.formPublicFileUrl(entity.getAvatarId()))
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
                .avatarUrl(UrlUtils.formPublicFileUrl(entity.getAvatarId()))
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
                .backgroundUrl(UrlUtils.formPublicFileUrl(entity.getBackgroundId()))
                .columns(emptyIfNull(entity.getColumns()).stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
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
                .owner(toBriefDto(entity.getOwner()))
                .collaborators(emptyIfNull(entity.getCollaborators()).stream()
                        .map(this::toBriefDto)
                        .collect(Collectors.toList()))
                .backgroundId(entity.getBackgroundId())
                .backgroundUrl(UrlUtils.formPublicFileUrl(entity.getBackgroundId()))
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
                .cards(emptyIfNull(entity.getCards()).stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public LabelDto toDto(Label entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return LabelDto.builder()
                .id(entity.getLabelId())
                .name(entity.getName())
                .color(entity.getColor())
                .boardId(entity.getBoard().getBoardId())
                .build();
    }

    public CardDto toDto(Card entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return CardDto.builder()
                .id(entity.getCardId())
                .content(entity.getContent())
                .order(entity.getOrder())
                .title(entity.getTitle())
                .authorId(entity.getAuthor().getUserId())
                .labels(emptyIfNull(entity.getLabels()).stream()
                        .map(this::toDto)
                        .collect(Collectors.toList()))
                .users(emptyIfNull(entity.getUsers()).stream()
                        .map(this::toBriefDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public CommonBackgroundDto toDto(CommonBackground entity) {
        if (Objects.isNull(entity)) {
            return null;
        }

        return CommonBackgroundDto.builder()
                .backgroundId(entity.getBackgroundId())
                .backgroundUrl(UrlUtils.formPublicFileUrl(entity.getBackgroundId()))
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

    public BoardColumn toEntity(BoardColumnDto dto) {
        Board board = boardsRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("board.not.found")));
        return BoardColumn.builder()
                .name(dto.getName())
                .order(dto.getOrder())
                .board(board)
                .build();
    }

    public Card toEntity(CardDto dto) {
        BoardColumn column = columnsRepository.findById(dto.getColumnId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("column.not.found")));
        User author = usersRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("user.not.found")));
        return Card.builder()
                .content(dto.getContent())
                .title(dto.getTitle())
                .order(dto.getOrder())
                .author(author)
                .column(column)
                .build();
    }

    public Label toEntity(LabelDto dto) {
        Board board = boardsRepository.findById(dto.getBoardId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("board.not.found")));
        return Label.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .board(board)
                .build();
    }
}
