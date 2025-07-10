package com.example.demo.repository;

import com.example.demo.model.Funcionario;
import com.example.demo.model.Departamento;
import com.example.demo.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
    
    // Busca funcionários pelo nome, ignorando maiúsculas/minúsculas
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    
    // Busca funcionários por email
    Funcionario findByEmail(String email);
    
    // Busca funcionários por CPF
    Funcionario findByCpf(String cpf);
    
    // Verifica se existe um funcionário com o email fornecido
    boolean existsByEmail(String email);
    
    // Verifica se existe um funcionário com o CPF fornecido
    boolean existsByCpf(String cpf);
    
    // Busca funcionários por departamento
    List<Funcionario> findByDepartamento(Departamento departamento);
    
    // Busca funcionários por cargo
    List<Funcionario> findByCargo(Cargo cargo);
    
    // Busca funcionários contratados após uma data específica
    List<Funcionario> findByDataContratacaoAfter(LocalDate data);
    
    // Busca funcionários com salário maior que o valor fornecido
    List<Funcionario> findBySalarioGreaterThan(java.math.BigDecimal salario);
    
    // Consulta personalizada para buscar funcionários por departamento e cargo
    @Query("SELECT f FROM Funcionario f WHERE f.departamento = :departamento AND f.cargo = :cargo")
    List<Funcionario> findByDepartamentoAndCargo(@Param("departamento") Departamento departamento, 
                                                 @Param("cargo") Cargo cargo);
    
    // Consulta personalizada para calcular o salário médio por departamento
    @Query("SELECT f.departamento.nome, AVG(f.salario) FROM Funcionario f GROUP BY f.departamento")
    List<Object[]> calcularSalarioMedioPorDepartamento();
} 