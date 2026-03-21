# TasteManager

TasteManager é uma aplicação desenvolvida em **Java 21** utilizando o framework **Spring Boot** e arquitetura em Clean Architecture. O objetivo do projeto é gerenciar usuários, restaurantes e seus cardápios, incluindo funcionalidades como criação, atualização, exclusão e validação de dados.
Nota: Optei por uma abordagem mais pragmática onde os serviços operam diretamente com DTOs para reduzir o boilerplate, ciente de que isso representa um leve desvio da Clean Architecture pura e acopla a camada de aplicação a de apresentação. Para a escala deste projeto, considerei um trade-off aceitável.

## 🏗️ Arquitetura

O sistema permite:
- Cadastro, atualização, deleção e consulta de usuários, tipos de usuário, restaurantes e itens de cardápio.
- Validação de login e troca de senha.
- Operações protegidas por validações de unicidade e integridade referencial.
- Documentação automática via Swagger/OpenAPI.

O código está organizado em camadas:
- **domain/**: Entidades de negócio e interfaces dos repositórios
- **application/**: Serviços e casos de uso da aplicação
- **infrastructure/**: Controllers REST, configurações e implementações técnicas
- **shared/**: DTOs, mappers, validators e exceptions compartilhados
  A separação visa facilitar manutenção, testes e evolução do sistema.

## Tecnologias Utilizadas
- **Java 21**
- **Spring Boot 3.2.5**
- **Maven**
- **MapStruct**
- **Docker** & **Docker Compose**
-  **Clean Architecture** (organização do código)
- **MySQL 8.0**
- **OpenAPI/Swagger**

## Pré-requisitos
- **Java 21** ou superior
- **Maven** 3.6 ou superior
- **Docker** e **Docker Compose**
- **MySQL** (usado via Docker Compose)

## Endpoints da API

Todos os endpoints seguem o padrão REST e estão versionados com `/api/v1/`.

### Usuário
- **POST** `/api/v1/user/create` — Cria usuário.
- **PATCH** `/api/v1/user/update/{id}` — Atualiza nome, email, tipo e endereço.
- **POST** `/api/v1/user/change-password/{id}` — Troca senha.
- **DELETE** `/api/v1/user/delete?id={id}` — Remove usuário.
- **POST** `/api/v1/user/validate-login` — Valida login e senha.
- **GET** `/api/v1/user/find-all?page={page}&size={size}` — Lista usuários paginados.
- **GET** `/api/v1/user/find-by-name?name={name}` — Busca usuários por nome.

### Tipo de Usuário
- **POST** `/api/v1/user-type/create` — Cria tipo de usuário.
- **GET** `/api/v1/user-type/find-all?page={page}&size={size}` — Lista tipos paginados.
- **GET** `/api/v1/user-type/find-by-id?id={id}` — Busca tipo por ID.
- **PATCH** `/api/v1/user-type/update/{id}` — Atualiza tipo.
- **DELETE** `/api/v1/user-type/delete?id={id}` — Remove tipo.

### Restaurante
- **POST** `/api/v1/restaurant/create` — Cria restaurante.
- **GET** `/api/v1/restaurant/find-all` — Lista restaurantes.
- **GET** `/api/v1/restaurant/find-by-id?id={id}` — Busca restaurante por ID.
- **PATCH** `/api/v1/restaurant/update/{id}` — Atualiza restaurante.
- **DELETE** `/api/v1/restaurant/delete?id={id}` — Remove restaurante.

### Itens do Cardápio (Menu)
- **POST** `/api/v1/menu/create/{restaurantId}` — Cria itens do cardápio.
- **GET** `/api/v1/menu/find-all?page={page}&size={size}` — Lista itens paginados.
- **GET** `/api/v1/menu/find-by-restaurant?id={restaurantId}` — Lista itens de um restaurante.
- **GET** `/api/v1/menu/find-by-id/{id}` — Busca item por ID.
- **PUT** `/api/v1/menu/update?restaurantId={restaurantId}&itemId={itemId}` — Atualiza item.
- **DELETE** `/api/v1/menu/delete-item?restaurantId={restaurantId}&restaurantItemNumber={restaurantItemNumber}` — Remove item.
- **DELETE** `/api/v1/menu/delete-all/{restaurantId}` — Remove todos os itens de um restaurante.


## Instruções de Configuração e Execução

### Pré-requisitos
- Java 21+
- Maven 3.6+
- Docker e Docker Compose

### Configuração
- O banco é populado via `import.sql`.
- O ambiente padrão é de desenvolvimento: `spring.jpa.hibernate.ddl-auto=create-drop` (os dados são apagados a cada reinicialização).
- Para produção, altere para `update` ou `validate` e ajuste variáveis de ambiente.

### Execução Local
1. Compile o projeto:
   ```bash
   mvn clean package
   ```
2. Construa a imagem Docker:
   ```bash
   docker build -t tastemanager-app .
   ```
3. Suba os containers e aplicação:
   ```bash
   docker-compose up --build
   ```
4. Acesse a aplicação em [http://localhost:8080](http://localhost:8080)

### Documentação da API
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

### Testes
- Testes unitários e de integração em `src/test/java`.
- Cobertura mínima de 80% exigida por JaCoCo.
- Execute:
   ```bash
   mvn test
   ```

## Contribuição
Contribuições são bem-vindas! Abra issues ou pull requests.

## Contato
Para mais informações, entre em contato com [Delaine] em [delaine@delaine].