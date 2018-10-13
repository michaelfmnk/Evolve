package com.dreamteam.api.repositories;

import com.dreamteam.api.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelsRepository extends JpaRepository<Label, Integer> {
}
