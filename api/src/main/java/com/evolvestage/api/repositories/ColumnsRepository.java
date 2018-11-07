package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ColumnsRepository extends JpaRepository<BoardColumn, Integer> {

    @Query("SELECT EXISTS(column) FROM BoardColumn column WHERE column.columnId = :columnId AND column.board.boardId = :boardId)")
    Boolean existsColumnByColumnIdAndBoardId(@Param("boardId") Integer boardId, @Param("columnId") Integer columnId);
}
