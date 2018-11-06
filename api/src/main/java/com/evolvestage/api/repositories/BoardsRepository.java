package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardsRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT column FROM Column column WHERE column.columnId = :columnId AND column.board.boardId = :boardId")
    Optional<Boolean> existsColumnByColumnIdAndBoardId(@Param("boardId") Integer boardId, @Param("columnId") Integer columnId);
}
