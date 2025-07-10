package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "cargos", indexes = {
    @jakarta.persistence.Index(name = "idx_cargo_nome", columnList = "nome"),
    @jakarta.persistence.Index(name = "idx_cargo_salario", columnList = "salario_base")
})
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Salário base é obrigatório")
    @DecimalMin(value = "0.01", message = "Salário base deve ser maior que zero")
    private BigDecimal salarioBase;

    // Construtor padrão (requerido pelo JPA)
    public Cargo() {}

    // Construtor conveniente
    public Cargo(String nome, String descricao, BigDecimal salarioBase) {
        this.nome = nome;
        this.descricao = descricao;
        this.salarioBase = salarioBase;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", salarioBase=" + salarioBase +
                '}';
    }
} 