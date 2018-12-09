package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ColumnsRepository extends JpaRepository<BoardColumn, Integer> {

    @Query("SELECT count(column) > 0 FROM BoardColumn column WHERE column.columnId = :columnId AND column.board.boardId = :boardId")
    Boolean existsColumnByColumnIdAndBoardId(@Param("boardId") Integer boardId, @Param("columnId") Integer columnId);

    @Modifying
    @Query("DELETE FROM BoardColumn column WHERE column.columnId = :columnId AND column.board.boardId = :boardId")
    void deleteColumnByColumnIdAndBoardId(@Param("boardId") Integer boardId, @Param("columnId") Integer columnId);
}
