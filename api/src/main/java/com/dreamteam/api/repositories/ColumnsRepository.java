package com.dreamteam.api.repositories;

import com.dreamteam.api.entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<BoardColumn, Integer> {
}
