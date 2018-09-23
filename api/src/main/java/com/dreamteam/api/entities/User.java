package com.dreamteam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime lastPasswordResetDate;
    private String password;
    private UUID avatarId;

    @OneToMany(mappedBy = "owner")
    private List<Board> ownBoards;

    @ManyToMany
    @JoinTable(
            name = "boards_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "board_id") }
    )
    private List<Board> joinedBoards;
}
