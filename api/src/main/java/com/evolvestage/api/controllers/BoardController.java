package com.evolvestage.api.controllers;

import com.evolvestage.api.dtos.BoardColumnDto;
import com.evolvestage.api.dtos.BoardDto;
import com.evolvestage.api.dtos.LabelDto;
import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.services.BoardColumnService;
import com.evolvestage.api.services.BoardService;
import com.evolvestage.api.services.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardColumnService boardColumnService;
    private final LabelService labelService;

    @PostMapping(Api.Boards.BOARDS)
    public BoardDto createBoard(@Validated @RequestBody BoardDto board, @ApiIgnore UserAuthentication auth) {
        board.setOwnerId(auth.getId());
        return boardService.createBoard(board);
    }

    @PostMapping(Api.Boards.BOARD_COLUMNS)
    public BoardColumnDto createColumn (@Validated @RequestBody BoardColumnDto column,
                                        @PathVariable(name = "board_id") Integer boardId) {
        column.setBoardId(boardId);
        return boardColumnService.createColumn(column);
    }

    @PostMapping(Api.Boards.BOARD_LABELS)
    @PreAuthorize("hasPermission(#boardId, 'OWN_BOARD', 'USER')")
    public LabelDto createLabel (@Validated @RequestBody LabelDto label,
                                 @PathVariable(name = "board_id") Integer boardId) {
        label.setBoardId(boardId);
        return labelService.createLabel(label);
    }

    @GetMapping(Api.Boards.BOARD)
    @PreAuthorize("hasPermission(#boardId, 'OWN_BOARD', 'USER')")
    public BoardDto getBoardById(@PathVariable(name = "board_id") Integer boardId) {
        return boardService.getBoardById(boardId);
    }
}
