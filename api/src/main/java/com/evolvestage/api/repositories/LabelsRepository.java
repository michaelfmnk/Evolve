package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelsRepository extends JpaRepository<Label, Integer> {
}
