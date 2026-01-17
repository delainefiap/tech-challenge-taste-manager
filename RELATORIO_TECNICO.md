# Relatório Técnico – TasteManager

## 1. Descrição da Arquitetura da Aplicação

A aplicação TasteManager foi desenvolvida em Java 21 utilizando o framework Spring Boot, seguindo princípios de arquitetura em camadas (Controller, Service, Repository, DTO, Entity, Validator, Exception, Mapper). O projeto adota boas práticas de SOLID, orientação a objetos e separação de responsabilidades. O banco de dados utilizado é MySQL, rodando em container Docker, e a aplicação também é containerizada, facilitando o deploy e a escalabilidade.

- **Camadas:**
  - Controller: expõe os endpoints REST.
  - Service: lógica de negócio.
  - Repository: acesso ao banco de dados (Spring Data JPA).
  - DTO: objetos de transferência de dados.
  - Entity: entidades persistidas no banco.
  - Validator: validações de negócio e dados.
  - Exception: tratamento centralizado de erros.
  - Mapper: conversão entre DTOs e entidades.

- **Documentação:** Swagger/OpenAPI disponível em `/swagger-ui.html`.
- **Testes:** Automatizados com JUnit e Mockito.

## 2. Modelagem das Entidades e Relacionamentos

- **User**
  - id (Long, PK)
  - name (String)
  - email (String, único)
  - login (String, único)
  - password (String)
  - last_update (Date)
  - address (String)
  - userTypeId (UserType, FK)

- **UserType**
  - id (Long, PK)
  - name (String, único)

Relacionamento: User (N) -> (1) UserType

## 3. Endpoints Disponíveis (com exemplos)

### Usuário
- **Criar Usuário:**
  - POST `/api/v1/user/create`
  - Exemplo:
    ```json
    {
      "name": "Ana Teste",
      "email": "ana.teste@email.com",
      "login": "anatestelogin",
      "password": "senhaAna123",
      "userTypeId": { "id": 1 },
      "address": "Rua Nova, 100"
    }
    ```
- **Atualizar Usuário:**
  - PATCH `/api/v1/user/update/{id}`
- **Trocar Senha:**
  - POST `/api/v1/user/change-password/{id}`
- **Deletar Usuário:**
  - DELETE `/api/v1/user/delete?id={id}`
- **Validar Login:**
  - POST `/api/v1/user/validate-login?login=anatestelogin&password=senhaAna123`
- **Listar Usuários:**
  - GET `/api/v1/user/find-all?page=1&size=40`
- **Buscar por Nome:**
  - GET `/api/v1/user/find-by-name?name=Ana`

### UserType
- **Criar Tipo:**
  - POST `/api/v1/usertype/create`
- **Atualizar Tipo:**
  - PATCH `/api/v1/usertype/update/{id}`
- **Deletar Tipo:**
  - DELETE `/api/v1/usertype/delete?id={id}`
- **Listar Tipos:**
  - GET `/api/v1/usertype/find-all?page=1&size=40`
- **Buscar por ID:**
  - GET `/api/v1/usertype/find/{id}`

## 4. Documentação Swagger (prints ou trechos)

Acesse: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Exemplo de resposta de erro (ProblemDetail):
```json
{
  "type": "https://example.com/validation-error",
  "title": "Validation Error",
  "status": 400,
  "detail": "Erro de validação nos campos enviados.",
  "instance": "/api/v1/user/create",
  "errors": {
    "email": "E-mail is mandatory.",
    "name": "You must provide a name."
  }
}
```

## 5. Descrição da Coleção Postman

A coleção Taste-Manager-Collection.postman_collection.json cobre:
- Cadastro de usuário válido e inválido
- Alteração de senha (sucesso e erro)
- Atualização de dados (sucesso e erro)
- Busca por nome
- Validação de login (sucesso e erro)

Exemplo de requisição de cadastro válido:
```json
{
  "name": "Ana Teste",
  "email": "ana.teste@email.com",
  "login": "anatestelogin",
  "password": "senhaAna123",
  "userTypeId": { "id": 1 },
  "address": "Rua Nova, 100"
}
```

## 6. Estrutura do Banco de Dados (tabelas)

- **Tabela user_types**
  - id (PK, auto_increment)
  - name (único)

- **Tabela users**
  - id (PK, auto_increment)
  - name
  - email (único)
  - login (único)
  - password
  - created_at
  - last_update
  - user_type_id (FK para user_types)
  - address

## 7. Passo a Passo para Executar com Docker Compose

1. Gere o JAR:
   ```shell
   mvn clean package
   ```
2. Construa a imagem Docker:
   ```shell
   docker build -t tastemanager-app .
   ```
3. Suba a aplicação e o banco:
   ```shell
   docker-compose up --build
   ```
4. Acesse a aplicação em [http://localhost:8080](http://localhost:8080)

**Variáveis de ambiente:**
- SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/tastemanager?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
- SPRING_DATASOURCE_USERNAME=tastemanager
- SPRING_DATASOURCE_PASSWORD=tastemanager

---

**Observação:**
- Prints da documentação Swagger e da coleção Postman devem ser inseridos no PDF final.
- Para converter este arquivo em PDF, use um editor Markdown ou Google Docs/Word.
