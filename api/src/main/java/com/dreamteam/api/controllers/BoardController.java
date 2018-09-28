package com.dreamteam.api.controllers;

import com.dreamteam.api.dtos.BoardDto;
import com.dreamteam.api.security.UserAuthentication;
import com.dreamteam.api.services.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping(Api.Boards.BOARDS)
    public BoardDto createBoard(@Validated @RequestBody BoardDto board, UserAuthentication auth) {
        board.setOwnerId(auth.getId());
        return boardService.createBoard(board);
    }
}
