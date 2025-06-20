package com.knexdesafio.knexdesafio.repositories;

import com.knexdesafio.knexdesafio.entities.Deputy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeputyRepository extends JpaRepository<Deputy, Long> {
    Optional<Deputy> findByCpf(String cpf);
    List<Deputy> findByUf(String uf);
}
