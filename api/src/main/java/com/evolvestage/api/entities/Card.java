package com.evolvestage.api.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "cards")
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardId;
    private String content;
    private String title;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @Column(name = "archived")
    @NotNull
    private Boolean archived = false;

    @Column(name = "order_num")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "column_id")
    private BoardColumn column;

    @OneToMany(mappedBy = "card")
    private List<CheckboxItem> checkboxList;

    @ManyToMany
    @JoinTable(
            name = "cards_labels",
            joinColumns = { @JoinColumn(name = "card_id") },
            inverseJoinColumns = { @JoinColumn(name = "label_id") }
    )
    private List<Label> labels;

    @OneToMany(mappedBy = "card")
    private List<Comment> comments;

    @ManyToMany
    @JoinTable(
            name = "cards_attachments",
            joinColumns = { @JoinColumn(name = "card_id") },
            inverseJoinColumns = { @JoinColumn(name = "attachment_id") }
    )
    private List<Attachment> attachments;


    @ManyToMany
    @JoinTable(
            name = "cards_users",
            joinColumns = { @JoinColumn(name = "card_id")},
            inverseJoinColumns = { @JoinColumn(name =  "user_id")}
    )
    private List<User> users;

}
