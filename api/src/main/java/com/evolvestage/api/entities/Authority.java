package com.evolvestage.api.entities;


import com.evolvestage.api.utils.TypeCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorityId;

    @Enumerated(EnumType.STRING)
    private Type authorityName;

    @Getter
    @AllArgsConstructor
    public enum Type implements TypeCreator<Authority> {
        ADMIN(1),
        USER(2);

        private int id;

        @Override
        public Authority getInstance() {
            return new Authority(id, this);
        }
    }
}
