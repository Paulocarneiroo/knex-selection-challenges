package com.knexdesafio.knexdesafio.repositories;

import com.knexdesafio.knexdesafio.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDeputyId(Long deputyId);
}