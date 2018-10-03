package com.dreamteam.api.services;

import com.dreamteam.api.dtos.BoardDto;
import com.dreamteam.api.dtos.UserDto;
import com.dreamteam.api.entities.Board;
import com.dreamteam.api.entities.User;
import com.dreamteam.api.repositories.UsersRepository;
import com.dreamteam.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class ConverterService {

    private UsersRepository usersRepository;
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

    public Board toEntity(BoardDto dto) {
        User owner = usersRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("user.not.found")));
        return Board.builder()
                .name(dto.getName())
                .owner(owner)
                .backgroundId(dto.getBackgroundId())
                .build();
    }

}
