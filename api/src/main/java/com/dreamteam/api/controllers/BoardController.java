package com.dreamteam.api.controllers;

import com.dreamteam.api.dtos.BoardColumnDto;
import com.dreamteam.api.dtos.BoardDto;
import com.dreamteam.api.security.UserAuthentication;
import com.dreamteam.api.services.BoardColumnService;
import com.dreamteam.api.services.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final BoardColumnService boardColumnService;

    @PostMapping(Api.Boards.BOARDS)
    public BoardDto createBoard(@Validated @RequestBody BoardDto board, @ApiIgnore UserAuthentication auth) {
        board.setOwnerId(auth.getId());
        return boardService.createBoard(board);
    }

    @PostMapping(Api.Boards.BOARD_COLUMNS)
    public BoardColumnDto createColumn (@Validated @RequestBody BoardColumnDto column, @PathVariable Integer board_id) {
        column.setBoardId(board_id);
        return boardColumnService.createColumn(column);
    }

}
