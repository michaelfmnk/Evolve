package com.dreamteam.api.repositories;

import com.dreamteam.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
