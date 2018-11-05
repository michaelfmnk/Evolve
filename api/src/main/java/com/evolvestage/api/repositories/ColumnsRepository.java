package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<BoardColumn, Integer> {
}
