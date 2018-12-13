package com.evolvestage.api.services;

import com.evolvestage.api.docs.DocsApiService;
import com.evolvestage.api.dtos.BoardBriefDto;
import com.evolvestage.api.dtos.BoardDto;
import com.evolvestage.api.entities.Board;
import com.evolvestage.api.entities.BoardInvitation;
import com.evolvestage.api.entities.User;
import com.evolvestage.api.exceptions.BadRequestException;
import com.evolvestage.api.listeners.events.BoardCreatedEvent;
import com.evolvestage.api.repositories.BoardsRepository;
import com.evolvestage.api.repositories.InvitationsRepository;
import com.evolvestage.api.repositories.UsersRepository;
import com.evolvestage.api.services.mail.MailjetService;
import com.evolvestage.api.services.mail.types.BoardInvitationEmail;
import com.evolvestage.api.services.mail.types.Email;
import com.evolvestage.api.services.mail.types.EvolveInvitationEmail;
import com.evolvestage.api.utils.MessagesService;
import com.evolvestage.api.utils.SecurityUtils;
import com.evolvestage.api.utils.UrlUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BoardService {


    private final ConverterService converter;
    private final BoardsRepository boardsRepository;
    private final UserService userService;
    private final UsersRepository usersRepository;
    private final CommonBackgroundService backgroundService;
    private final MessagesService messagesService;
    private final DocsApiService docsApiService;
    private final ApplicationEventPublisher eventPublisher;
    private final StringRedisTemplate redisTemplate;
    private final MailjetService mailjetService;
    private final InvitationsRepository invitationsRepository;

    @Transactional
    public BoardDto createBoard(BoardDto boardDto) {
        Board boardEntity = converter.toEntity(boardDto);
        boardEntity = boardsRepository.save(boardEntity);
        saveBackground(boardEntity.getBackgroundId(), boardEntity.getBoardId());
        eventPublisher.publishEvent(BoardCreatedEvent.builder()
                .boardId(boardEntity.getBoardId())
                .userId(boardEntity.getOwner().getUserId())
                .boardName(boardEntity.getName())
                .build());
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

    public void invite(Integer boardId, List<String> usersToInvite) {
        Board board = findValidBoard(boardId);
        List<String> emails = board.getCollaborators().stream().map(User::getEmail).collect(Collectors.toList());
        usersToInvite.removeIf(emails::contains);
        usersToInvite.forEach(email -> processInvitation(email, board));
    }

    @SneakyThrows
    private void processInvitation(String email, Board board) {
        UUID code = UUID.randomUUID();
        BoardInvitation invitation = BoardInvitation.builder()
                .email(email)
                .boardId(board.getBoardId())
                .build();
        invitationsRepository.putInvitation(code.toString(), invitation);
        boolean exists = usersRepository.existsByEmail(email);
        Email emailToSend;
        if (!exists) {
            emailToSend = BoardInvitationEmail.builder()
                    .to(email)
                    .link(UrlUtils.formAcceptInvitationUrl(code, board.getBoardId()))
                    .boardName(board.getName())
                    .build();
        } else {
            emailToSend = EvolveInvitationEmail.builder()
                    .to(email)
                    .boardName(board.getName())
                    .build();

        }
        mailjetService.sendEmail(emailToSend);
    }

    @SneakyThrows
    public void acceptInvitation(String code, String currentUsersEmail) {
        BoardInvitation invitation = invitationsRepository.findInvitationsByCode(code);
        if (Objects.isNull(invitation)) {
            throw new EntityNotFoundException("invitation.not.found");
        }
        if (!Objects.equals(currentUsersEmail, invitation.getEmail())) {
            throw new BadRequestException(messagesService.getMessage("invitation.not.valid"));
        }
        invitationsRepository.removeByCode(code);
        Board board = findValidBoard(invitation.getBoardId());
        User user = userService.findValidUserById(SecurityUtils.getUserIdFromSecurityContext());
        board.getCollaborators().add(user);
        boardsRepository.save(board);
    }
}
