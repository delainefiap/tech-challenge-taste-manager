# TasteManager

TasteManager é uma aplicação desenvolvida em **Java 21** utilizando o framework **Spring Boot** e **Clean Architecture**. O objetivo do projeto é gerenciar usuários, restaurantes e seus cardápios, incluindo funcionalidades como criação, atualização, exclusão e validação de dados.

## 🏗️ Arquitetura

Esta aplicação segue os princípios da **Clean Architecture**, organizando o código em camadas bem definidas:

- **📁 domain/**: Entidades de negócio e interfaces dos repositórios
- **📁 application/**: Serviços e casos de uso da aplicação
- **📁 infrastructure/**: Controllers REST, configurações e implementações técnicas
- **📁 shared/**: DTOs, mappers, validators e exceptions compartilhados


## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.5**
- **Maven**
- **MapStruct** (para mapeamento de DTOs)
- **Docker** & **Docker Compose**
- **MySQL 8.0** (banco de dados)
- **OpenAPI/Swagger** (documentação da API)
- **Clean Architecture** (organização do código)

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

## Endpoints de Tipo de Usuário

### 1. Criar Tipo de Usuário
- **POST** `/api/v1/user-type/create`
- **Descrição**: Cria um novo tipo de usuário.
- **Regras**:
    - `name` é obrigatório e único.
- **Resposta**: `201 Created`

### 2. Listar Tipos de Usuário
- **GET** `/api/v1/user-type/find-all?page={page}&size={size}`
- **Descrição**: Lista tipos de usuário com paginação.
- **Resposta**: `200 OK`

### 3. Buscar Tipo de Usuário por ID
- **GET** `/api/v1/user-type/find-by-id?id={id}`
- **Descrição**: Busca um tipo de usuário específico.
- **Resposta**: `200 OK` ou `404 Not Found`

### 4. Atualizar Tipo de Usuário
- **PATCH** `/api/v1/user-type/update/{id}`
- **Descrição**: Atualiza um tipo de usuário existente.
- **Resposta**: `200 OK` ou `404 Not Found`

### 5. Deletar Tipo de Usuário
- **DELETE** `/api/v1/user-type/delete?id={id}`
- **Descrição**: Remove um tipo de usuário.
- **Resposta**: `200 OK` ou `404 Not Found`

## Endpoints de Restaurante

### 1. Criar Restaurante
- **POST** `/api/v1/restaurant/create`
- **Descrição**: Cria um novo restaurante.
- **Regras**:
    - `name`, `address`, `typeKitchen`, `openingHours` e `ownerId` são obrigatórios.
    - `ownerId` deve referenciar um usuário existente.
- **Resposta**: `201 Created`

### 2. Listar Restaurantes
- **GET** `/api/v1/restaurant/find-all`
- **Descrição**: Lista todos os restaurantes.
- **Resposta**: `200 OK`

### 3. Buscar Restaurante por ID
- **GET** `/api/v1/restaurant/find-by-id?id={id}`
- **Descrição**: Busca um restaurante específico.
- **Resposta**: `200 OK` ou `404 Not Found`

### 4. Atualizar Restaurante
- **PATCH** `/api/v1/restaurant/update/{id}`
- **Descrição**: Atualiza um restaurante existente.
- **Resposta**: `200 OK` ou `404 Not Found`

### 5. Deletar Restaurante
- **DELETE** `/api/v1/restaurant/delete?id={id}`
- **Descrição**: Remove um restaurante.
- **Resposta**: `200 OK` ou `404 Not Found`

## Endpoints de Itens do Cardápio (MenuItem)

### 1. Criar Item do Cardápio
- **POST** `/api/v1/menu/create/{restaurantId}`
- **Descrição**: Cria uma lista de itens do cardápio para o restaurante informado.
- **Regras**:
    - `name`, `description` e `price` são obrigatórios.
    - `price` deve ser positivo.
    - `restaurantId` deve referenciar um restaurante existente.
    - `imagePath` é opcional (caminho da foto do prato).
    - `availableOnlyAtRestaurant` indica se o item só pode ser consumido no local.
- **Resposta**: `201 Created`

### 2. Listar Todos os Itens
- **GET** `/api/v1/menu/find-all?page={page}&size={size}`
- **Descrição**: Lista todos os itens do cardápio com paginação.
- **Resposta**: `200 OK`

### 3. Listar Itens de um Restaurante
- **GET** `/api/v1/menu/find-by-restaurant?id={restaurantId}`
- **Descrição**: Lista os itens do cardápio de um restaurante específico.
- **Resposta**: `200 OK` ou `404 Not Found`

### 4. Buscar Item por ID
- **GET** `/api/v1/menu/find-by-id/{id}`
- **Descrição**: Busca um item específico do cardápio.
- **Resposta**: `200 OK` ou `404 Not Found`

### 5. Atualizar Item do Cardápio
- **PUT** `/api/v1/menu/update?restaurantId={restaurantId}&itemId={itemId}`
- **Descrição**: Atualiza um item do cardápio.
- **Resposta**: `200 OK` ou `404 Not Found`

### 6. Deletar Item do Cardápio
- **DELETE** `/api/v1/menu/delete-item?restaurantId={restaurantId}&restaurantItemNumber={restaurantItemNumber}`
- **Descrição**: Remove um item do cardápio associado a um restaurante.
- **Resposta**: `200 OK` ou `404 Not Found`

### 7. Deletar Todos os Itens de um Restaurante
- **DELETE** `/api/v1/menu/delete-all/{restaurantId}`
- **Descrição**: Remove todos os itens do cardápio de um restaurante.
- **Resposta**: `200 OK` ou `404 Not Found`

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

Abaixo está a estrutura das principais tabelas conforme as entidades do código (source of truth).

### Tabela: `users`

| Campo         | Tipo         | Constraints                                    | Descrição                                                |
|---------------|--------------|------------------------------------------------|----------------------------------------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT                    | Identificador único do usuário                           |
| name          | VARCHAR(255) | (sem restrição de coluna no DB)                | Nome completo do usuário (validação aplicada na aplicação)
| email         | VARCHAR(255) | NOT NULL, UNIQUE                               | E-mail único do usuário                                  |
| login         | VARCHAR(255) | NOT NULL, UNIQUE, updatable = false            | Login único (definido na criação; não pode ser alterado) |
| password      | VARCHAR(255) | (sem restrição de coluna no DB)                | Senha do usuário (validação aplicada na aplicação)      |
| created_at    | TIMESTAMP    | NOT NULL                                       | Data de criação (preenchida automaticamente pela aplicação)
| last_update   | TIMESTAMP    | NULL                                           | Data da última modificação (atualizada automaticamente)
| user_type_id  | BIGINT       | NOT NULL, FOREIGN KEY -> user_types(id)        | Referência ao tipo de usuário                            |
| address       | VARCHAR(500) | NULL                                           | Endereço completo do usuário                             |

Observações:
- A entidade `User` define um índice `idx_user_type` sobre a coluna `user_type_id`.
- Algumas regras (por exemplo, obrigatoriedade de `name` ou `password`) são aplicadas pela camada de validação da aplicação e podem não estar refletidas como constraints de coluna no banco de dados.

### Tabela: `user_types`

| Campo         | Tipo         | Constraints                     | Descrição                                                |
|---------------|--------------|---------------------------------|----------------------------------------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT     | Identificador único do tipo de usuário                   |
| name          | VARCHAR(255) | NOT NULL, UNIQUE                | Nome do tipo de usuário (ex.: CLIENTE, DONO_RESTAURANTE) |
| description   | VARCHAR(255) | NULL                            | Descrição opcional do tipo de usuário                   |

### Tabela: `restaurants`

| Campo         | Tipo         | Constraints                     | Descrição                                                |
|---------------|--------------|---------------------------------|----------------------------------------------------------|
| id            | BIGINT       | PRIMARY KEY, AUTO_INCREMENT     | Identificador único do restaurante                       |
| name          | VARCHAR(255) | NOT NULL, UNIQUE                | Nome único do restaurante                                 |
| address       | VARCHAR(500) | NOT NULL                        | Endereço completo do restaurante                         |
| type_kitchen  | VARCHAR(255) | NOT NULL                        | Tipo de cozinha (ex: Brasileira, Italiana)              |
| opening_hours | VARCHAR(100) | NOT NULL                        | Horário de funcionamento                                 |
| owner_id      | BIGINT       | NOT NULL, FOREIGN KEY -> users(id) | Referência ao usuário dono do restaurante               |

### Tabela: `menu`

> Observação: a entidade que representa itens do cardápio é `Menu` (tabela `menu`).

| Campo                        | Tipo         | Constraints                     | Descrição                                                |
|------------------------------|--------------|---------------------------------|----------------------------------------------------------|
| id                           | BIGINT       | PRIMARY KEY, AUTO_INCREMENT     | Identificador único do item                              |
| restaurant_id                | BIGINT       | NOT NULL, FOREIGN KEY -> restaurants(id) | Referência ao restaurante                         |
| restaurant_item_number       | BIGINT       | NOT NULL                        | Número do item no cardápio do restaurante (identificador local)
| name                         | VARCHAR(255) | NOT NULL                        | Nome do item                                             |
| description                  | VARCHAR(500) | NOT NULL                        | Descrição do item                                        |
| price                        | DOUBLE       | NOT NULL                        | Preço do item                                            |
| image_path                   | VARCHAR(255) | NULL                            | Caminho para a foto do item                              |
| available_only_at_restaurant | BOOLEAN      | NOT NULL, DEFAULT FALSE         | Se o item só pode ser consumido no restaurante           |

### Observações gerais
- Os nomes das tabelas e colunas acima foram alinhados com as anotações JPA presentes nas entidades em `src/main/java/br/com/tastemanager/domain/entity`.
- Regras adicionais de negócio e validações (por exemplo: obrigatoriedade, formatos, valores mínimos) são aplicadas na camada de validação da aplicação e nas DTOs; algumas dessas restrições podem não aparecer como constraints diretas no esquema do banco.
- Se desejar, posso gerar um diagrama simples (MER) com essas quatro tabelas e suas FK ou criar scripts SQL DDL aproximados para referência.


## Regras Gerais de Validação

- **name**: obrigatório, não pode ser nulo, vazio ou apenas espaços.
- **email**: obrigatório, único e válido (deve conter @).
- **login**: obrigatório, único, definido na criação e não pode ser alterado.
- **password**: obrigatório na criação, só pode ser alterado pelo endpoint de troca de senha.
- **typePerson**: (nota) o banco usa `user_type_id` -> referenciar `user_types`; na aplicação o fluxo trata os tipos de usuário esperados (cliente, dono_restaurante).
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