package com.dreamteam.api.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "checklist_items")
public class CheckboxItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer itemId;
    private String content;
    private Boolean done;
    @Column(name = "order_num")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
