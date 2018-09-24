package com.dreamteam.api.repositories;

import com.dreamteam.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
}
