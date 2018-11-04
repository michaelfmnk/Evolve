package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardsRepository extends JpaRepository<Card, Integer> {

    @Query("SELECT card FROM Card card WHERE card.cardId = :cardId AND card.column.board.boardId = :boardId")
    Optional<Card> findCardByBoardIdAndCardId(@Param("boardId") Integer boardId, @Param("cardId") Integer cardId);
}
