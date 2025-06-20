package com.knexdesafio.knexdesafio.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_expense")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDate emissionDate;
    private String supplier;
    private BigDecimal netValue;
    private String urlDocument;
    @ManyToOne @JoinColumn(name = "deputy_id")
    private Deputy deputy;
}
