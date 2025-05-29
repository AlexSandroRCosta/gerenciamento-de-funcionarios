-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS gerenciamento_funcionarios;
USE gerenciamento_funcionarios;

-- Criar tabela de departamentos
CREATE TABLE IF NOT EXISTS departamentos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

-- Criar tabela de cargos
CREATE TABLE IF NOT EXISTS cargos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    departamento_id INT,
    salario_base DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (departamento_id) REFERENCES departamentos(id)
);

-- Criar tabela de funcionarios
CREATE TABLE IF NOT EXISTS funcionarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_nascimento DATE NOT NULL,
    data_contratacao DATE NOT NULL,
    cargo_id INT,
    salario DECIMAL(10,2) NOT NULL,
    status ENUM('ATIVO', 'INATIVO') DEFAULT 'ATIVO',
    FOREIGN KEY (cargo_id) REFERENCES cargos(id)
);

-- Inserir alguns departamentos de exemplo
INSERT INTO departamentos (nome, descricao) VALUES
('TI', 'Departamento de Tecnologia da Informação'),
('RH', 'Recursos Humanos'),
('Financeiro', 'Departamento Financeiro'),
('Vendas', 'Departamento Comercial');

-- Inserir alguns cargos de exemplo
INSERT INTO cargos (nome, departamento_id, salario_base) VALUES
('Desenvolvedor Java', 1, 5000.00),
('Analista de RH', 2, 3500.00),
('Contador', 3, 4500.00),
('Vendedor', 4, 3000.00); 