package com.evolvestage.api.repositories;

import com.evolvestage.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select distinct user from User user " +
            "left join user.joinedBoards as joined " +
            "left join user.ownBoards as own " +
            "where user.userId in :userIds and (joined.boardId = :boardId or own.boardId = :boardId)")
    List<User> findUsersInBoard(@Param("boardId") Integer boardId, @Param("userIds") Collection<Integer> userIds);
}
