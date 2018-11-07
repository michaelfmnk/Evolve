package com.evolvestage.api.services;

import com.evolvestage.api.entities.Card;
import com.evolvestage.api.listeners.events.CardArchivedEvent;
import com.evolvestage.api.repositories.CardsRepository;
import com.evolvestage.api.utils.MessagesService;
import com.evolvestage.api.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CardService {

    private final MessagesService messagesService;
    private final CardsRepository cardsRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void archiveCard(Integer boardId, Integer cardId) {
        Card cardEntity = findValidCard(boardId, cardId);
        cardEntity.setArchived(true);
        eventPublisher.publishEvent(CardArchivedEvent.builder()
                .boardId(boardId)
                .cardId(cardId)
                .cardTitle(cardEntity.getTitle())
                .userId(SecurityUtils.getUserIdFromSecurityContext())
                .build());
        cardsRepository.save(cardEntity);
    }

    public void deleteCard(Integer boardId, Integer cardId) {
        Card cardEntity = findValidCard(boardId, cardId);
        cardsRepository.delete(cardEntity);
    }

    private Card findValidCard(Integer boardId, Integer cardId) {
        return cardsRepository.findCardByBoardIdAndCardId(boardId, cardId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("card.not.found")));
    }

}
