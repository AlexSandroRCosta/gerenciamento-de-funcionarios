package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.Index;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "funcionarios", indexes = {
    @jakarta.persistence.Index(name = "idx_funcionario_nome", columnList = "nome"),
    @jakarta.persistence.Index(name = "idx_funcionario_email", columnList = "email"),
    @jakarta.persistence.Index(name = "idx_funcionario_cpf", columnList = "cpf"),
    @jakarta.persistence.Index(name = "idx_funcionario_departamento", columnList = "departamento_id"),
    @jakarta.persistence.Index(name = "idx_funcionario_cargo", columnList = "cargo_id"),
    @jakarta.persistence.Index(name = "idx_funcionario_data_contratacao", columnList = "data_contratacao")
})
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @Column(nullable = false, unique = true, length = 14)
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "^[0-9]{3}\\.[0-9]{3}\\.[0-9]{3}-[0-9]{2}$", message = "CPF deve estar no formato XXX.XXX.XXX-XX")
    private String cpf;

    @Column(nullable = false)
    @NotNull(message = "Data de contratação é obrigatória")
    @Past(message = "Data de contratação deve ser no passado")
    private LocalDate dataContratacao;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Salário é obrigatório")
    @DecimalMin(value = "0.01", message = "Salário deve ser maior que zero")
    private BigDecimal salario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "departamento_id")
    @NotNull(message = "Departamento é obrigatório")
    private Departamento departamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cargo_id")
    @NotNull(message = "Cargo é obrigatório")
    private Cargo cargo;

    // Construtor padrão (requerido pelo JPA)
    public Funcionario() {}

    // Construtor conveniente
    public Funcionario(String nome, String email, String cpf, LocalDate dataContratacao, 
                      BigDecimal salario, Departamento departamento, Cargo cargo) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataContratacao = dataContratacao;
        this.salario = salario;
        this.departamento = departamento;
        this.cargo = cargo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", dataContratacao=" + dataContratacao +
                ", salario=" + salario +
                ", departamento=" + departamento.getNome() +
                ", cargo=" + cargo.getNome() +
                '}';
    }
} 