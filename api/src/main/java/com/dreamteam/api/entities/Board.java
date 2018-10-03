package com.dreamteam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "boards")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boardId;
    private String name;
    private UUID backgroundId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "board")
    private List<BoardColumn> columns;

    @ManyToMany
    @JoinTable(
            name = "boards_labels",
            joinColumns = { @JoinColumn(name = "board_id") },
            inverseJoinColumns = { @JoinColumn(name = "label_id") }
    )
    private List<Label> presentLabels;
}
