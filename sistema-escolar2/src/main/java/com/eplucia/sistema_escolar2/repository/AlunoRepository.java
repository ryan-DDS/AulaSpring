package com.eplucia.sistema_escolar2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eplucia.sistema_escolar2.model.Aluno;

// O Spring Data JPA fornece operações do BD prontas
public interface AlunoRepository extends JpaRepository<Aluno, Long> {}
