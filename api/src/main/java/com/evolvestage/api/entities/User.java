package com.evolvestage.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String email;
    private String firstName;
    private String lastName;
    private Date lastPasswordResetDate;
    private String password;
    private UUID avatarId;
    private boolean enabled;

    @OneToMany(mappedBy = "owner")
    private List<Board> ownBoards;

    @ManyToMany
    @JoinTable(
            name = "boards_users",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "board_id") }
    )
    private List<Board> joinedBoards;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_authorities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> authorities;
}
