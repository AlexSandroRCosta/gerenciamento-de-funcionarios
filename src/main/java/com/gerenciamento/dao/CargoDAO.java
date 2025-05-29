package com.gerenciamento.dao;

import com.gerenciamento.model.Cargo;
import com.gerenciamento.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CargoDAO {
    
    public void inserir(Cargo cargo) throws SQLException {
        String sql = "INSERT INTO cargos (nome, departamento_id, salario_base) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, cargo.getNome());
            stmt.setInt(2, cargo.getDepartamentoId());
            stmt.setDouble(3, cargo.getSalarioBase());
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cargo.setId(rs.getInt(1));
                }
            }
        }
    }
    
    public List<Cargo> listar() throws SQLException {
        List<Cargo> cargos = new ArrayList<>();
        String sql = """
            SELECT c.*, d.nome as departamento_nome 
            FROM cargos c 
            LEFT JOIN departamentos d ON c.departamento_id = d.id 
            ORDER BY c.nome
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cargo c = new Cargo();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setDepartamentoId(rs.getInt("departamento_id"));
                c.setSalarioBase(rs.getDouble("salario_base"));
                
                // Criar objeto Departamento para o cargo
                if (rs.getInt("departamento_id") > 0) {
                    c.setDepartamento(new Departamento(
                        rs.getInt("departamento_id"),
                        rs.getString("departamento_nome"),
                        null
                    ));
                }
                
                cargos.add(c);
            }
        }
        
        return cargos;
    }
    
    public Cargo buscarPorId(int id) throws SQLException {
        String sql = """
            SELECT c.*, d.nome as departamento_nome 
            FROM cargos c 
            LEFT JOIN departamentos d ON c.departamento_id = d.id 
            WHERE c.id = ?
            """;
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cargo c = new Cargo();
                    c.setId(rs.getInt("id"));
                    c.setNome(rs.getString("nome"));
                    c.setDepartamentoId(rs.getInt("departamento_id"));
                    c.setSalarioBase(rs.getDouble("salario_base"));
                    
                    if (rs.getInt("departamento_id") > 0) {
                        c.setDepartamento(new Departamento(
                            rs.getInt("departamento_id"),
                            rs.getString("departamento_nome"),
                            null
                        ));
                    }
                    
                    return c;
                }
            }
        }
        
        return null;
    }
    
    public void atualizar(Cargo cargo) throws SQLException {
        String sql = "UPDATE cargos SET nome = ?, departamento_id = ?, salario_base = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cargo.getNome());
            stmt.setInt(2, cargo.getDepartamentoId());
            stmt.setDouble(3, cargo.getSalarioBase());
            stmt.setInt(4, cargo.getId());
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM cargos WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
} 