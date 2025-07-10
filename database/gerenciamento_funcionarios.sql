-- Script de inicialização do banco de dados para MySQL 8.0+
-- Sistema de Gerenciamento de Funcionários (compatível)

CREATE DATABASE IF NOT EXISTS gerenciamento_funcionarios
  CHARACTER SET utf8mb4 
  COLLATE utf8mb4_unicode_ci;
USE gerenciamento_funcionarios;

-- Tabela de Departamentos
CREATE TABLE IF NOT EXISTS departamentos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_departamento_nome (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Cargos
CREATE TABLE IF NOT EXISTS cargos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    salario_base DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_cargo_nome (nome),
    INDEX idx_cargo_salario (salario_base)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabela de Funcionários
CREATE TABLE IF NOT EXISTS funcionarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(200) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    data_contratacao DATE NOT NULL,
    salario DECIMAL(10,2) NOT NULL,
    departamento_id BIGINT NOT NULL,
    cargo_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (departamento_id) REFERENCES departamentos(id) ON DELETE RESTRICT,
    FOREIGN KEY (cargo_id) REFERENCES cargos(id) ON DELETE RESTRICT,
    INDEX idx_funcionario_nome (nome),
    INDEX idx_funcionario_email (email),
    INDEX idx_funcionario_cpf (cpf),
    INDEX idx_funcionario_departamento (departamento_id),
    INDEX idx_funcionario_cargo (cargo_id),
    INDEX idx_funcionario_data_contratacao (data_contratacao)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dados iniciais para departamentos
INSERT INTO departamentos (nome, descricao) VALUES 
('Tecnologia da Informação', 'Departamento responsável pelo desenvolvimento e manutenção de sistemas'),
('Recursos Humanos', 'Departamento responsável pela gestão de pessoas e processos de RH'),
('Financeiro', 'Departamento responsável pela gestão financeira e contábil da empresa'),
('Marketing', 'Departamento responsável pela comunicação e estratégias de marketing'),
('Vendas', 'Departamento responsável pela comercialização de produtos e serviços'),
('Operações', 'Departamento responsável pelas operações diárias da empresa'),
('Jurídico', 'Departamento responsável pelos assuntos legais e contratos'),
('Qualidade', 'Departamento responsável pela garantia de qualidade dos processos')
AS new
ON DUPLICATE KEY UPDATE descricao = new.descricao, updated_at = CURRENT_TIMESTAMP;

-- Dados iniciais para cargos
INSERT INTO cargos (nome, descricao, salario_base) VALUES 
('Desenvolvedor Júnior', 'Desenvolvedor de software em início de carreira', 3500.00),
('Desenvolvedor Pleno', 'Desenvolvedor de software com experiência intermediária', 5500.00),
('Desenvolvedor Sênior', 'Desenvolvedor de software com ampla experiência', 7500.00),
('Analista de Sistemas', 'Analista responsável por análise e design de sistemas', 6000.00),
('Gerente de Projetos', 'Gerente responsável por coordenar projetos de TI', 8500.00),
('Analista de RH', 'Analista responsável por processos de recursos humanos', 4500.00),
('Coordenador de RH', 'Coordenador responsável pela área de recursos humanos', 6500.00),
('Contador', 'Profissional responsável pela contabilidade', 5000.00),
('Analista Financeiro', 'Analista responsável por análises financeiras', 5500.00),
('Analista de Marketing', 'Analista responsável por estratégias de marketing', 4800.00),
('Coordenador de Marketing', 'Coordenador responsável pela área de marketing', 6800.00),
('Vendedor', 'Profissional responsável pela venda de produtos/serviços', 3200.00),
('Gerente de Vendas', 'Gerente responsável pela equipe de vendas', 7200.00),
('Analista de Operações', 'Analista responsável por processos operacionais', 4200.00),
('Advogado', 'Profissional responsável pelos assuntos jurídicos', 6000.00),
('Analista de Qualidade', 'Analista responsável pela garantia de qualidade', 4500.00)
AS new
ON DUPLICATE KEY UPDATE descricao = new.descricao, salario_base = new.salario_base, updated_at = CURRENT_TIMESTAMP;

-- Dados iniciais para funcionários
INSERT INTO funcionarios (nome, email, cpf, data_contratacao, salario, departamento_id, cargo_id) VALUES 
('João Silva Santos', 'joao.silva@empresa.com', '123.456.789-00', '2023-01-15', 3800.00, 1, 1),
('Maria Santos Oliveira', 'maria.santos@empresa.com', '987.654.321-00', '2022-06-01', 5800.00, 1, 2),
('Pedro Oliveira Costa', 'pedro.oliveira@empresa.com', '456.789.123-00', '2021-03-20', 7800.00, 1, 3),
('Ana Costa Ferreira', 'ana.costa@empresa.com', '789.123.456-00', '2022-09-10', 6200.00, 1, 4),
('Carlos Ferreira Lima', 'carlos.ferreira@empresa.com', '321.654.987-00', '2020-11-25', 8800.00, 1, 5),
('Lucia Pereira Silva', 'lucia.pereira@empresa.com', '654.321.987-00', '2023-02-05', 4700.00, 2, 6),
('Roberto Lima Santos', 'roberto.lima@empresa.com', '147.258.369-00', '2022-08-15', 6700.00, 2, 7),
('Fernanda Rocha Costa', 'fernanda.rocha@empresa.com', '258.369.147-00', '2022-04-15', 5200.00, 3, 8),
('Ricardo Almeida Silva', 'ricardo.almeida@empresa.com', '369.147.258-00', '2023-01-20', 5700.00, 3, 9),
('Patricia Souza Lima', 'patricia.souza@empresa.com', '159.357.486-00', '2023-03-10', 5000.00, 4, 10),
('Marcelo Santos Costa', 'marcelo.santos@empresa.com', '357.486.159-00', '2022-07-22', 7000.00, 4, 11),
('Juliana Costa Silva', 'juliana.costa@empresa.com', '753.951.486-00', '2023-05-01', 3500.00, 5, 12),
('Andre Silva Lima', 'andre.silva@empresa.com', '951.486.753-00', '2022-12-10', 7500.00, 5, 13),
('Camila Lima Santos', 'camila.lima@empresa.com', '486.753.951-00', '2023-04-15', 4400.00, 6, 14),
('Diego Santos Costa', 'diego.santos@empresa.com', '753.486.951-00', '2022-10-05', 6200.00, 7, 15),
('Vanessa Costa Lima', 'vanessa.costa@empresa.com', '951.753.486-00', '2023-06-20', 4700.00, 8, 16)
AS new
ON DUPLICATE KEY UPDATE nome = new.nome, email = new.email, cpf = new.cpf, data_contratacao = new.data_contratacao, salario = new.salario, departamento_id = new.departamento_id, cargo_id = new.cargo_id, updated_at = CURRENT_TIMESTAMP; 