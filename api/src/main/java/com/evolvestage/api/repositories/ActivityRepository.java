package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.Activity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, Integer> {
    Page<Activity> findByBoardId(Integer boardId, Pageable pageable);
}
