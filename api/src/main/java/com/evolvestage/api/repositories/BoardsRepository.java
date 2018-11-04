package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardsRepository extends JpaRepository<Board, Integer> {
}
