package com.example.demo.repository;

import com.example.demo.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    
    // Busca cargos pelo nome, ignorando maiúsculas/minúsculas
    List<Cargo> findByNomeContainingIgnoreCase(String nome);
    
    // Verifica se existe um cargo com o nome fornecido
    boolean existsByNome(String nome);
    
    // Busca cargos com salário base maior que o valor fornecido
    List<Cargo> findBySalarioBaseGreaterThan(java.math.BigDecimal salarioBase);
} 