# Sistema de Gerenciamento de Funcionários

Sistema web desenvolvido em Spring Boot para gerenciamento completo de funcionários, departamentos e cargos, utilizando páginas HTML (Thymeleaf) e navegação tradicional.

## 🚀 Funcionalidades

- **Gestão de Funcionários**: Cadastro, edição, exclusão e consulta de funcionários
- **Gestão de Departamentos**: Administração de departamentos da empresa
- **Gestão de Cargos**: Controle de cargos e salários base
- **Relatórios**: Visualização de estatísticas e relatórios

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot 3.x, Spring Data JPA, Spring MVC
- **Frontend**: Thymeleaf, Bootstrap 5, JavaScript
- **Banco de Dados**: MySQL 8.0+
- **Build Tool**: Maven
- **Java**: JDK 17+

## 📋 Pré-requisitos

- Java JDK 17 ou superior
- MySQL 8.0 ou superior
- Maven 3.6+

## 🗄️ Banco de Dados

### Estrutura Melhorada

O banco de dados foi completamente revisado e adequado ao projeto com as seguintes melhorias:

#### Tabelas Principais

1. **departamentos**
   - `id` (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
   - `nome` (VARCHAR(100), UNIQUE, NOT NULL)
   - `descricao` (VARCHAR(500))
   - `created_at` (TIMESTAMP)
   - `updated_at` (TIMESTAMP)

2. **cargos**
   - `id` (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
   - `nome` (VARCHAR(100), UNIQUE, NOT NULL)
   - `descricao` (VARCHAR(500))
   - `salario_base` (DECIMAL(10,2), NOT NULL, CHECK > 0)
   - `created_at` (TIMESTAMP)
   - `updated_at` (TIMESTAMP)

3. **funcionarios**
   - `id` (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
   - `nome` (VARCHAR(200), NOT NULL)
   - `email` (VARCHAR(255), UNIQUE, NOT NULL)
   - `cpf` (VARCHAR(14), UNIQUE, NOT NULL)
   - `data_contratacao` (DATE, NOT NULL)
   - `salario` (DECIMAL(10,2), NOT NULL, CHECK > 0)
   - `departamento_id` (BIGINT, FOREIGN KEY)
   - `cargo_id` (BIGINT, FOREIGN KEY)
   - `created_at` (TIMESTAMP)
   - `updated_at` (TIMESTAMP)

#### Melhorias Implementadas

✅ **Constraints de Validação**
- Validação de formato de email (Bean Validation + regex)
- Validação de formato de CPF (Bean Validation + regex)
- Verificação de data de contratação (não pode ser futura)
- Salários devem ser positivos (Bean Validation)
- Validações em tempo de execução na aplicação

✅ **Integridade Referencial**
- Foreign keys com ON DELETE RESTRICT
- Índices otimizados para performance
- Constraints de unicidade

✅ **Auditoria**
- Campos `created_at` e `updated_at` em todas as tabelas
- Rastreamento automático de alterações

✅ **Performance**
- Índices estratégicos para consultas frequentes
- Otimização de tipos de dados
- Configuração de charset UTF8MB4

✅ **Inicialização Automática**
- Hibernate cria as tabelas automaticamente com índices
- Dados iniciais inseridos via data.sql
- Validações Bean Validation no nível da aplicação
- Configuração otimizada para desenvolvimento

✅ **Dados de Exemplo**
- 8 departamentos realistas
- 16 cargos com salários base adequados
- 15 funcionários distribuídos por departamentos
- Dados organizados e coerentes

## ⚙️ Configuração

### 1. Configuração do Banco de Dados

```properties
# application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/gerenciamento_funcionarios?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=sua_senha_aqui
```

### 2. Inicialização do Banco de Dados

O banco de dados será criado automaticamente pelo Spring Boot na primeira execução.

**Opção Manual (se necessário):**
   ```bash
mysql -u root -p < database/gerenciamento_funcionarios.sql
   ```

### 3. Executando a Aplicação

```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📊 Estrutura de Dados

### Departamentos Disponíveis
- Tecnologia da Informação
- Recursos Humanos
- Financeiro
- Marketing
- Vendas
- Operações
- Jurídico
- Qualidade

### Cargos Disponíveis
- Desenvolvedor Júnior/Pleno/Sênior
- Analista de Sistemas
- Gerente de Projetos
- Analista/Coordenador de RH
- Contador
- Analista Financeiro
- Analista/Coordenador de Marketing
- Vendedor/Gerente de Vendas
- Analista de Operações
- Advogado
- Analista de Qualidade

## 🔧 Desenvolvimento

### Estrutura do Projeto
```
src/
├── main/
│   ├── java/com/example/demo/
│   │   ├── controller/     # Controladores MVC
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositórios Spring Data
│   │   ├── service/        # Camada de serviços
│   │   └── config/         # Configurações
│   └── resources/
│       ├── templates/      # Templates Thymeleaf
│       ├── data.sql        # Dados iniciais
│       ├── migration.sql   # Script de migração (opcional)
│       └── application.properties
```

## 🚀 Deploy

### Produção
```bash
# Gerar JAR
mvn clean package

# Executar
java -jar target/gerenciamento-de-funcionarios-0.0.1-SNAPSHOT.jar
```

## 📝 Licença

Este projeto está sob a licença MIT.

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📞 Suporte

Para dúvidas ou suporte, entre em contato através dos issues do projeto. 