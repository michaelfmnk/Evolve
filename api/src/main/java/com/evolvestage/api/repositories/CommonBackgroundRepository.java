package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.CommonBackground;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommonBackgroundRepository extends JpaRepository<CommonBackground, UUID> {
}
