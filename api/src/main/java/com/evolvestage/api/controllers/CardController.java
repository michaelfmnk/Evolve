package com.evolvestage.api.controllers;

import com.evolvestage.api.dtos.CardBriefDto;
import com.evolvestage.api.security.UserAuthentication;
import com.evolvestage.api.services.CardService;
import com.evolvestage.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(Api.ROOT)
@AllArgsConstructor
public class CardController {

    private final CardService cardService;
    private final MessagesService messagesService;

    @PostMapping(Api.Boards.BOARD_CARDS)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public CardBriefDto createCard(@PathVariable(name = "board_id") Integer boardId,
                              @PathVariable(name = "column_id") Integer columnId,
                              @ApiIgnore UserAuthentication auth,
                              @Validated @RequestBody CardBriefDto card) {
        if(!cardService.isColumnValid(boardId, columnId)) {
           throw new  EntityNotFoundException(messagesService.getMessage("column.not.found"));
        }
        card.setColumnId(columnId);
        card.setAuthorId(auth.getId());
        return cardService.createCard(card);
    }

    @DeleteMapping(Api.Boards.BOARD_CARD_BY_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_OWNER', 'USER')")
    public void deleteCard(@PathVariable("board_id") Integer boardId,
                           @PathVariable("card_id") Integer cardId) {
        cardService.deleteCard(boardId, cardId);
    }

    @PatchMapping(Api.Boards.BOARD_CARDS_ARCHIVE_CARD)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_OWNER', 'USER')")
    public void archiveCard(@PathVariable("board_id") Integer boardId,
                            @PathVariable("card_id") Integer cardId) {
        cardService.archiveCard(boardId, cardId);
    }

    @PatchMapping(Api.Boards.MOVE_CARD_BY_ID)
    @PreAuthorize("hasPermission(#boardId, 'BOARD_COLLABORATOR', 'USER')")
    public CardBriefDto moveCard(@PathVariable(name = "board_id") Integer boardId,
                                   @PathVariable(name = "column_id") Integer columnId,
                                   @PathVariable(name = "card_id") Integer cardId,
                                   @PathVariable(name = "destination_id") Integer destinationId) {
        return cardService.moveCard(cardId, columnId, boardId, destinationId);
    }
}
