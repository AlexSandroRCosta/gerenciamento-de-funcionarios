package com.example.demo.repository;

import com.example.demo.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    
    // Busca departamentos pelo nome, ignorando maiúsculas/minúsculas
    List<Departamento> findByNomeContainingIgnoreCase(String nome);
    
    // Verifica se existe um departamento com o nome fornecido
    boolean existsByNome(String nome);
} 