package com.gerenciamento.model;

public class Cargo {
    private int id;
    private String nome;
    private int departamentoId;
    private double salarioBase;
    private Departamento departamento;

    public Cargo() {}

    public Cargo(int id, String nome, int departamentoId, double salarioBase) {
        this.id = id;
        this.nome = nome;
        this.departamentoId = departamentoId;
        this.salarioBase = salarioBase;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(int departamentoId) {
        this.departamentoId = departamentoId;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
} 