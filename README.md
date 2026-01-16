# TasteManager

TasteManager é uma aplicação desenvolvida em **Java 21** utilizando o framework **Spring Boot**. O objetivo do projeto é gerenciar usuários e suas informações, incluindo funcionalidades como criação, atualização, exclusão, troca de senha e validação de login.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Maven**
- **MapStruct** (para mapeamento de DTOs)
- **Docker** (recomendado, para containerização)
- **Banco de Dados SQL** (MySQL via Docker Compose)

## Pré-requisitos

- **Java 21** ou superior
- **Maven**
- **Docker**
- **MySQL** (usado via Docker Compose)

## Configuração do Projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/delainefiap/taste-manager.git
   ```

2. Gere o arquivo JAR do projeto:
   ```shell
   mvn clean package
   ```
   
3. Construa a imagem Docker:
   ```bash
   docker build -t tastemanager-app .
   ```

4. Suba a aplicação com Docker Compose (MySQL + App):
   ```bash
   docker-compose up --build
   ```

5. Acesse a aplicação:
    - **URL base**: `http://localhost:8080`

6. Observação:
    - A aplicação utiliza o banco MySQL por padrão via Docker Compose.
    - Para rodar localmente sem Docker, ajuste o `spring.datasource.url` para `localhost` no `application.properties`.
    - O banco é criado automaticamente e há script para dados iniciais.

## Endpoints da API

### 1. Criar Usuário
- **POST** `/user/create`
- **Descrição**: Cria um novo usuário.
- **Regras**:
    - `login` deve ser único.
    - `name`, `email`, `login` e `password` são obrigatórios e válidos.
- **Resposta**: `201 Created`

### 2. Atualizar Usuário
- **PUT** `/user/update/{id}`
- **Descrição**: Atualiza dados permitidos de um usuário existente.
- **Campos permitidos**: `name`, `email`, `typePerson`, `address`
- **Regras**:
    - `login` e `password` não podem ser alterados nesse endpoint.
    - `name` e `email` devem ser válidos.
- **Resposta**: `200 OK`

### 3. Trocar Senha
- **PUT** `/user/update-password/{id}`
- **Descrição**: Altera a senha do usuário.
- **Regras**:
    - Senha atual deve estar correta.
    - Nova senha não pode ser nula ou em branco.
- **Resposta**: `200 OK`

### 4. Deletar Usuário
- **DELETE** `/user/delete/{id}`
- **Descrição**: Remove um usuário.
- **Regras**:
    - Usuário deve existir.
- **Resposta**: `200 OK`

### 5. Validar Login
- **POST** `/user/login`
- **Descrição**: Verifica se login e senha são válidos.
- **Regras**:
    - `login` e `password` não podem estar vazios.
- **Resposta**:
    - `200 OK` se credenciais forem válidas.
    - `401 Unauthorized` se inválidas.

### 6. Listar Usuários
- **GET** `/user/all`
- **Descrição**: Lista usuários com suporte à paginação.
- **Parâmetros**:
    - `size` (opcional): número de usuários por página.
    - `offset` (opcional): posição inicial.
- **Resposta**: `200 OK` com lista paginada.

## Regras Gerais de Validação

- **name**: obrigatório, não pode ser nulo, vazio ou apenas espaços.
- **email**: obrigatório e válido.
- **typePerson**: deve ser `customer` ou `restaurant_owner`.
- **address**: opcional, mas válido se fornecido.
- **login**: definido na criação, não pode ser alterado.
- **password**: só pode ser alterado pelo endpoint de troca de senha.

## Testes
Para executar os testes:
```bash
mvn test
```


## Contribuição

Contribuição
Contribuições são bem-vindas! Fique à vontade para abrir issues e pull requests

Contato:
Para mais informações, entre em contato com [Delaine] em [delaine@delaine].