# Sistema de Gerenciamento de Funcionários

Sistema web para gerenciamento de funcionários, desenvolvido em Java utilizando Servlets, JSP, JDBC e MySQL.

## Requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- MySQL 8.0 ou superior
- Tomcat 10.0 ou superior

## Configuração

1. Clone o repositório:
```bash
git clone https://github.com/seu-usuario/gerenciamento-de-funcionarios.git
cd gerenciamento-de-funcionarios
```

2. Configure o banco de dados:
- Crie um banco de dados MySQL chamado `gerenciamento_funcionarios`
- Execute o script SQL localizado em `src/main/resources/database.sql`

3. Configure as credenciais do banco de dados:
- Abra o arquivo `src/main/resources/database.properties`
- Atualize as credenciais conforme seu ambiente:
```properties
db.url=jdbc:mysql://localhost:3306/gerenciamento_funcionarios
db.user=seu_usuario
db.password=sua_senha
```

4. Compile o projeto:
```bash
mvn clean package
```

5. Implante o arquivo WAR gerado no diretório `target` no seu servidor Tomcat.

## Funcionalidades

### Departamentos
- Listar departamentos
- Adicionar novo departamento
- Editar departamento existente
- Excluir departamento

### Cargos
- Listar cargos
- Adicionar novo cargo
- Editar cargo existente
- Excluir cargo
- Vincular cargo a departamento
- Definir salário base

### Funcionários
- Listar funcionários
- Adicionar novo funcionário
- Editar funcionário existente
- Excluir funcionário
- Filtrar funcionários por departamento
- Buscar funcionários por nome ou CPF

## Tecnologias Utilizadas

- Java 17
- Jakarta EE Web API
- JSP/JSTL
- JDBC
- MySQL
- Bootstrap 5
- Font Awesome
- Maven

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── gerenciamento/
│   │           ├── dao/
│   │           ├── dto/
│   │           ├── servlet/
│   │           └── util/
│   ├── resources/
│   │   ├── database.properties
│   │   └── database.sql
│   └── webapp/
│       └── WEB-INF/
│           ├── views/
│           │   ├── cargos/
│           │   ├── departamentos/
│           │   ├── funcionarios/
│           │   └── erro/
│           └── web.xml
```

## Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes. 