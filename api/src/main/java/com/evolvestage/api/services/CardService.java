package com.evolvestage.api.services;

import com.evolvestage.api.dtos.CardBriefDto;
import com.evolvestage.api.dtos.CardDto;
import com.evolvestage.api.entities.Card;
import com.evolvestage.api.listeners.events.CardArchivedEvent;
import com.evolvestage.api.repositories.CardsRepository;
import com.evolvestage.api.repositories.ColumnsRepository;
import com.evolvestage.api.utils.MessagesService;
import com.evolvestage.api.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class CardService {

    private final ConverterService converter;
    private final MessagesService messagesService;
    private final CardsRepository cardsRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ColumnsRepository columnsRepository;

    public CardBriefDto createCard(CardBriefDto cardBriefDto) {
        Card cardEntity = converter.toEntity(cardBriefDto);
        cardEntity = cardsRepository.save(cardEntity);
        return converter.toBriefDto(cardEntity);
    }

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

    public Boolean isColumnValid(Integer boardId, Integer columnId) {
        return columnsRepository.existsColumnByColumnIdAndBoardId(boardId, columnId);
    }

    private Card findValidCard(Integer boardId, Integer cardId) {
        return cardsRepository.findCardByBoardIdAndCardId(boardId, cardId)
                .orElseThrow(() -> new EntityNotFoundException(messagesService.getMessage("card.not.found")));
    }

}
