package com.gerenciamento.dao;

import com.gerenciamento.model.Funcionario;
import com.gerenciamento.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    
    public void inserir(Funcionario funcionario) throws SQLException {
        String sql = """
            INSERT INTO funcionarios 
            (nome, email, cpf, data_nascimento, data_contratacao, cargo_id, salario, status) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEmail());
            stmt.setString(3, funcionario.getCpf());
            stmt.setDate(4, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            stmt.setDate(5, new java.sql.Date(funcionario.getDataContratacao().getTime()));
            stmt.setInt(6, funcionario.getCargoId());
            stmt.setDouble(7, funcionario.getSalario());
            stmt.setString(8, funcionario.getStatus());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    funcionario.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public List<Funcionario> listar() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = """
            SELECT f.*, c.nome as cargo_nome, c.salario_base, d.nome as departamento_nome
            FROM funcionarios f
            LEFT JOIN cargos c ON f.cargo_id = c.id
            LEFT JOIN departamentos d ON c.departamento_id = d.id
            ORDER BY f.nome
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setId(rs.getInt("id"));
                f.setNome(rs.getString("nome"));
                f.setEmail(rs.getString("email"));
                f.setCpf(rs.getString("cpf"));
                f.setDataNascimento(rs.getDate("data_nascimento"));
                f.setDataContratacao(rs.getDate("data_contratacao"));
                f.setCargoId(rs.getInt("cargo_id"));
                f.setSalario(rs.getDouble("salario"));
                f.setStatus(rs.getString("status"));
                
                // Criar objeto Cargo para o funcionário
                if (rs.getInt("cargo_id") > 0) {
                    Cargo cargo = new Cargo();
                    cargo.setId(rs.getInt("cargo_id"));
                    cargo.setNome(rs.getString("cargo_nome"));
                    cargo.setSalarioBase(rs.getDouble("salario_base"));
                    
                    // Criar objeto Departamento para o cargo
                    if (rs.getString("departamento_nome") != null) {
                        cargo.setDepartamento(new Departamento(
                            rs.getInt("departamento_id"),
                            rs.getString("departamento_nome"),
                            null
                        ));
                    }
                    
                    f.setCargo(cargo);
                }
                
                funcionarios.add(f);
            }
        }
        
        return funcionarios;
    }
    
    public Funcionario buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT f.*, c.nome as cargo_nome, c.salario_base, d.nome as departamento_nome
            FROM funcionarios f
            LEFT JOIN cargos c ON f.cargo_id = c.id
            LEFT JOIN departamentos d ON c.departamento_id = d.id
            WHERE f.id = ?
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setId(rs.getInt("id"));
                    f.setNome(rs.getString("nome"));
                    f.setEmail(rs.getString("email"));
                    f.setCpf(rs.getString("cpf"));
                    f.setDataNascimento(rs.getDate("data_nascimento"));
                    f.setDataContratacao(rs.getDate("data_contratacao"));
                    f.setCargoId(rs.getInt("cargo_id"));
                    f.setSalario(rs.getDouble("salario"));
                    f.setStatus(rs.getString("status"));
                    
                    if (rs.getInt("cargo_id") > 0) {
                        Cargo cargo = new Cargo();
                        cargo.setId(rs.getInt("cargo_id"));
                        cargo.setNome(rs.getString("cargo_nome"));
                        cargo.setSalarioBase(rs.getDouble("salario_base"));
                        
                        if (rs.getString("departamento_nome") != null) {
                            cargo.setDepartamento(new Departamento(
                                rs.getInt("departamento_id"),
                                rs.getString("departamento_nome"),
                                null
                            ));
                        }
                        
                        f.setCargo(cargo);
                    }
                    
                    return f;
                }
            }
        }
        
        return null;
    }
    
    public void atualizar(Funcionario funcionario) throws SQLException {
        String sql = """
            UPDATE funcionarios 
            SET nome = ?, email = ?, cpf = ?, data_nascimento = ?, 
                data_contratacao = ?, cargo_id = ?, salario = ?, status = ?
            WHERE id = ?
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEmail());
            stmt.setString(3, funcionario.getCpf());
            stmt.setDate(4, new java.sql.Date(funcionario.getDataNascimento().getTime()));
            stmt.setDate(5, new java.sql.Date(funcionario.getDataContratacao().getTime()));
            stmt.setInt(6, funcionario.getCargoId());
            stmt.setDouble(7, funcionario.getSalario());
            stmt.setString(8, funcionario.getStatus());
            stmt.setInt(9, funcionario.getId());
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM funcionarios WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    public List<Funcionario> buscarPorDepartamento(int departamentoId) throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = """
            SELECT f.*, c.nome as cargo_nome, c.salario_base, d.nome as departamento_nome
            FROM funcionarios f
            LEFT JOIN cargos c ON f.cargo_id = c.id
            LEFT JOIN departamentos d ON c.departamento_id = d.id
            WHERE d.id = ?
            ORDER BY f.nome
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, departamentoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Funcionario f = new Funcionario();
                    f.setId(rs.getInt("id"));
                    f.setNome(rs.getString("nome"));
                    f.setEmail(rs.getString("email"));
                    f.setCpf(rs.getString("cpf"));
                    f.setDataNascimento(rs.getDate("data_nascimento"));
                    f.setDataContratacao(rs.getDate("data_contratacao"));
                    f.setCargoId(rs.getInt("cargo_id"));
                    f.setSalario(rs.getDouble("salario"));
                    f.setStatus(rs.getString("status"));
                    
                    if (rs.getInt("cargo_id") > 0) {
                        Cargo cargo = new Cargo();
                        cargo.setId(rs.getInt("cargo_id"));
                        cargo.setNome(rs.getString("cargo_nome"));
                        cargo.setSalarioBase(rs.getDouble("salario_base"));
                        
                        if (rs.getString("departamento_nome") != null) {
                            cargo.setDepartamento(new Departamento(
                                rs.getInt("departamento_id"),
                                rs.getString("departamento_nome"),
                                null
                            ));
                        }
                        
                        f.setCargo(cargo);
                    }
                    
                    funcionarios.add(f);
                }
            }
        }
        
        return funcionarios;
    }
} 