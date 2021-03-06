package com.evolvestage.api.controllers;

import com.evolvestage.api.dtos.*;
import com.evolvestage.api.dtos.containers.EmailsContainer;
import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.services.ActivityService;
import com.evolvestage.api.services.BoardColumnService;
import com.evolvestage.api.services.BoardService;
import com.evolvestage.api.services.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardColumnService boardColumnService;
    private final ActivityService activityService;
    private final LabelService labelService;

    @PostMapping(Api.Boards.BOARDS)
    public BoardDto createBoard(@Validated @RequestBody BoardDto board, @ApiIgnore UserAuthentication auth) {
        board.setOwnerId(auth.getId());
        return boardService.createBoard(board);
    }

    @PostMapping(Api.Boards.BOARD_COLUMNS)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public BoardColumnDto createColumn(@PathVariable(name = "board_id") Integer boardId,
                                       @Validated @RequestBody BoardColumnDto column) {
        column.setBoardId(boardId);
        return boardColumnService.createColumn(column);
    }

    @PostMapping(Api.Boards.BOARD_LABELS)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public LabelDto createLabel(@Validated @RequestBody LabelDto label,
                                @PathVariable(name = "board_id") Integer boardId) {
        label.setBoardId(boardId);
        return labelService.createLabel(label);
    }

    @GetMapping(Api.Boards.BOARD_BY_ID)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public BoardDto getBoardById(@PathVariable(name = "board_id") Integer boardId) {
        return boardService.getBoardById(boardId);
    }

    @DeleteMapping(Api.Boards.BOARD_BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_OWNER', 'USER')")
    public void deleteBoard(@PathVariable("board_id") Integer boardId) {
        boardService.deleteBoard(boardId);
    }

    @PutMapping(Api.Boards.BOARD_BY_ID)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public BoardBriefDto updateBoard(@PathVariable("board_id") Integer boardId,
                                     @RequestBody @Validated BoardBriefDto boardBriefDto) {
        boardBriefDto.setId(boardId);
        return boardService.updateBoard(boardId, boardBriefDto);
    }

    @GetMapping(Api.Boards.BOARD_ACTIVITIES)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public Pagination<ActivityDto> getActivities(@PathVariable("board_id") Integer boardId, Pageable pageable) {
        return activityService.getBoardActivity(boardId, pageable);
    }

    @DeleteMapping(Api.Boards.BOARD_COLUMN_BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public void deleteColumn(@PathVariable("board_id") Integer boardId, @PathVariable("column_id") Integer columnId) {
        boardColumnService.deleteColumn(boardId, columnId);
    }

    @PutMapping(Api.Boards.BOARD_COLUMN_BY_ID)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public BoardColumnDto updateColumn(@PathVariable("board_id") Integer boardId, @PathVariable("column_id") Integer columnId,
                             @Validated @RequestBody BoardColumnDto columnDto) {
        return boardColumnService.updateColumn(boardId, columnId, columnDto);
    }

    @PostMapping(Api.Boards.BOARD_COLLABORATORS)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public void invite(@PathVariable("board_id") Integer boardId,
                       @Valid @RequestBody EmailsContainer emailsContainer) {
        boardService.invite(boardId, emailsContainer.getEmails());
    }

    @PostMapping(Api.Boards.INVITATION_BY_ID)
    public void acceptInvitation(@RequestBody VerificationCodeDto codeDto,
                                 @ApiIgnore UserAuthentication authentication) {
        boardService.acceptInvitation(codeDto.getCode(), authentication.getEmail());
    }

}
