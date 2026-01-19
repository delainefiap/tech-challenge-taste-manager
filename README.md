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
- **Maven** 3.6 ou superior
- **Docker** e **Docker Compose**
- **MySQL** (usado via Docker Compose)

## Endpoints da API

**Versionamento**: Todos os endpoints utilizam o prefixo `/api/v1/` para versionamento da API, seguindo boas práticas REST.

### 1. Criar Usuário
- **POST** `/api/v1/user/create`
- **Descrição**: Cria um novo usuário.
- **Regras**:
    - `login` deve ser único.
    - `name`, `email`, `login` e `password` são obrigatórios e válidos.
- **Resposta**: `201 Created`

### 2. Atualizar Usuário
- **PATCH** `/api/v1/user/update/{id}`
- **Descrição**: Atualiza dados permitidos de um usuário existente.
- **Campos permitidos**: `name`, `email`, `typePerson`, `address`
- **Regras**:
    - `login` e `password` não podem ser alterados nesse endpoint.
    - `name` e `email` devem ser válidos.
- **Resposta**: `200 OK`

### 3. Trocar Senha
- **POST** `/api/v1/user/change-password/{id}`
- **Descrição**: Altera a senha do usuário.
- **Regras**:
    - Senha atual deve estar correta.
    - Nova senha não pode ser nula ou em branco.
- **Resposta**: `200 OK`

### 4. Deletar Usuário
- **DELETE** `/api/v1/user/delete?id={id}`
- **Descrição**: Remove um usuário.
- **Regras**:
    - Usuário deve existir.
- **Resposta**: `200 OK`

### 5. Validar Login
- **POST** `/api/v1/user/validate-login`
- **Descrição**: Verifica se login e senha são válidos consultando os dados no banco de dados.
- **Regras**:
    - `login` e `password` não podem estar vazios.
    - Verifica se existe um usuário com o login informado.
    - Valida se a senha fornecida corresponde à senha armazenada.
- **Resposta**:
    - `200 OK` se credenciais forem válidas.
    - `401 Unauthorized` se inválidas.

### 6. Listar Usuários
- **GET** `/api/v1/user/find-all?page={page}&size={size}`
- **Descrição**: Lista usuários com suporte à paginação.
- **Parâmetros**:
    - `page` (obrigatório): número da página.
    - `size` (obrigatório): número de usuários por página.
- **Resposta**: `200 OK` com lista paginada.

### 7. Buscar Usuários por Nome
- **GET** `/api/v1/user/find-by-name?name={name}`
- **Descrição**: Busca usuários cujo nome contenha o termo informado (case-insensitive).
- **Regras**:
    - Retorna lista de usuários encontrados.
    - Retorna erro se não houver correspondência.
- **Resposta**: `200 OK` com lista de usuários.

## Documentação Swagger/OpenAPI

A documentação interativa dos endpoints está disponível em:
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

Na interface Swagger, você pode visualizar exemplos de requisições e respostas de sucesso e erro (incluindo ProblemDetail).

### Padrão ProblemDetail (RFC 7807)

O sistema implementa o padrão RFC 7807 para padronizar as respostas de erro da aplicação. 

Exemplo de resposta de erro (ProblemDetail):
```json
{
  "type": "https://example.com/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Erro de validação nos campos enviados.",
  "instance": "/api/v1/user",
  "errors": {
    "email": "E-mail is mandatory.",
    "name": "You must provide a name."
  }
}
```

## Coleção Postman

O projeto inclui uma coleção Postman completa (`Taste-Manager-Collection.postman_collection.json`) na raiz do repositório, cobrindo os seguintes cenários:

- **Cadastro de usuário válido**: Criação de usuário com todos os campos corretos.
- **Cadastro inválido**: Tentativas de cadastro com e-mail duplicado, campos obrigatórios faltando, etc.
- **Alteração de senha**: Endpoint exclusivo para troca de senha com sucesso e erro.
- **Atualização de dados**: Endpoint distinto para atualizar informações do usuário (nome, email, etc.).
- **Busca de usuários por nome**: Testes de busca com diferentes critérios.
- **Validação de login**: Testes de autenticação com credenciais válidas e inválidas.
- **Listagem paginada**: Consulta de usuários com diferentes parâmetros de paginação.

Para importar a coleção no Postman:
1. Abra o Postman
2. Clique em "Import"
3. Selecione o arquivo `Taste-Manager-Collection.postman_collection.json`
4. Execute as requisições após subir a aplicação com Docker Compose

## Estrutura do Banco de Dados

### Tabela: `users`

| Campo         | Tipo         | Constraints                     | Descrição                                                |
|---------------|--------------|---------------------------------|----------------------------------------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT     | Identificador único do usuário                           |
| name          | VARCHAR(255) | NOT NULL                        | Nome completo do usuário                                 |
| email         | VARCHAR(255) | NOT NULL, UNIQUE                | E-mail único do usuário                                  |
| login         | VARCHAR(100) | NOT NULL, UNIQUE                | Login único (não pode ser alterado)                      |
| password      | VARCHAR(255) | NOT NULL                        | Senha do usuário                                         |
| created_at    | TIMESTAMP    | NOT NULL, DEFAULT CURRENT_TIMESTAMP | Data de criação do registro                              |
| last_update   | TIMESTAMP    | NULL, ON UPDATE CURRENT_TIMESTAMP   | Data da última modificação                               |
| type_person   | VARCHAR(1)   | NULL                            | Tipo de usuário: '1' (cliente) ou '2' (dono_restaurante) |
| address       | VARCHAR(500) | NULL                            | Endereço completo do usuário                             |

**Observações**:
- O campo `email` possui constraint de unicidade garantida pelo banco de dados.
- O campo `login` possui constraint de unicidade e não pode ser atualizado após criação.
- O campo `created_at` é definido automaticamente no momento da inserção.
- O campo `last_update` é atualizado automaticamente em qualquer modificação do registro.

## Regras Gerais de Validação

- **name**: obrigatório, não pode ser nulo, vazio ou apenas espaços.
- **email**: obrigatório, único e válido (deve conter @).
- **login**: obrigatório, único, definido na criação e não pode ser alterado.
- **password**: obrigatório na criação, só pode ser alterado pelo endpoint de troca de senha.
- **typePerson**: deve ser `1` para `cliente` ou `2` para `dono_restaurante`.
- **address**: opcional, mas válido se fornecido.
- **createdAt**: gerado automaticamente no momento da criação.
- **lastUpdate**: atualizado automaticamente sempre que o usuário é modificado.

## Tipos de Usuário

O sistema contempla dois tipos de usuário:

1. **Cliente** (`typePerson = "1"`)
    - Usuário que pode consultar restaurantes, fazer pedidos e deixar avaliações.

2. **Dono de Restaurante** (`typePerson = "2"`)
    - Usuário responsável pela gestão de um ou mais restaurantes.

## Testes

O projeto inclui testes unitários automatizados usando **JUnit 5** e **Mockito**.

Para executar os testes:
```bash
mvn test
```

Os testes cobrem:
- **Controllers**: Validação de endpoints e respostas HTTP
- **Services**: Lógica de negócio e regras de validação
- **Mappers**: Conversão entre DTOs e entidades
- **Validators**: Validações customizadas
- **Exception Handlers**: Tratamento de erros e ProblemDetail

## Execução com Docker Compose

### Variáveis de Ambiente

O arquivo `docker-compose.yml` configura as seguintes variáveis de ambiente:

**MySQL:**
- `MYSQL_ROOT_PASSWORD=root`: Senha do usuário root do MySQL
- `MYSQL_DATABASE=tastemanager`: Nome do banco de dados
- `MYSQL_USER=tastemanager`: Usuário da aplicação
- `MYSQL_PASSWORD=tastemanager`: Senha do usuário da aplicação

**Aplicação:**
- `TZ=America/Sao_Paulo`: Timezone da aplicação
- `SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/tastemanager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true`: URL de conexão com o banco
- `SPRING_DATASOURCE_USERNAME=tastemanager`: Usuário do banco
- `SPRING_DATASOURCE_PASSWORD=tastemanager`: Senha do banco

### Passo a Passo para Execução

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/delainefiap/taste-manager.git
   ```

2. **Gere o arquivo JAR do projeto**:
   ```bash
   mvn clean package
   ```

3. Construa a imagem Docker:
   ```bash
   docker build -t tastemanager-app .
   ```   

4. **Suba a aplicação com Docker Compose** (MySQL + App):
   ```bash
   docker-compose up --build
   ```

5. **Acesse a aplicação**:
    - URL base: `http://localhost:8080`
    - Swagger UI: `http://localhost:8080/swagger-ui.html`

6. **Verifique os logs** (opcional):
   ```bash
   docker-compose logs -f app
   ```

7. Como para a aplicação:
```bash
   docker-compose down
   ```


### Observações Importantes

- A aplicação utiliza **MySQL 8.0** rodando em container Docker.
- O banco de dados é criado automaticamente através do script `init.sql`.
- Dados iniciais são inseridos através do script `data.sql`.
- A aplicação aguarda o MySQL estar saudável antes de iniciar (healthcheck configurado).
- Para rodar localmente sem Docker, ajuste o `spring.datasource.url` para `localhost` no `application.properties`.

### Estrutura de Volumes

O Docker Compose configura um volume persistente para o MySQL:
- `db_data:/var/lib/mysql`: Mantém os dados do banco mesmo após parar os containers.

Para resetar completamente o banco de dados:
```bash
docker-compose down -v
docker-compose up --build
```


## Contribuição

Contribuição
Contribuições são bem-vindas! Fique à vontade para abrir issues e pull requests

Contato:
Para mais informações, entre em contato com [Delaine] em [delaine@delaine].