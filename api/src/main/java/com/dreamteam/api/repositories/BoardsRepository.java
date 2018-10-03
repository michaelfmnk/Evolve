package com.dreamteam.api.repositories;

import com.dreamteam.api.entities.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardsRepository extends JpaRepository<Board, Integer> {
}
