package com.evolvestage.api.services;

import com.evolvestage.api.entities.Card;
import com.evolvestage.api.repositories.CardsRepository;
import com.evolvestage.api.utils.MessagesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CardService {

    private final MessagesService messagesService;
    private final CardsRepository cardsRepository;

    public void archiveCard(Integer boardId, Integer cardId) {
        Card cardEntity = cardsRepository.findCardByBoardIdAndCardId(boardId, cardId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("card.not.found")));
        cardEntity.setArchived(true);
        cardsRepository.save(cardEntity);
    }
}
