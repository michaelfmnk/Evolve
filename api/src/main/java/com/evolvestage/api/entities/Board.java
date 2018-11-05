package com.evolvestage.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;
    private String name;
    private UUID backgroundId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "boards_users",
            joinColumns = { @JoinColumn(name = "board_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> collaborators;

    @OneToMany(mappedBy = "board")
    private List<BoardColumn> columns;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Label> presentLabels;
}
