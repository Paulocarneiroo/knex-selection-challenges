package com.knexdesafio.knexdesafio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_deputy")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Deputy {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String uf;
    private String cpf;
    private String party;

    @Setter(AccessLevel.NONE) @OneToMany(mappedBy = "deputy")
    private List<Expense> expenses = new ArrayList<>();

}
