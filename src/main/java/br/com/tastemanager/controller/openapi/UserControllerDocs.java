package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface UserControllerDocs {
    @Operation(
        summary = "Realiza a criação de um usuário.",
        description = "Cria um novo usuário no sistema e retorna os dados do usuário criado.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"Ana Teste\",\n  \"email\": \"ana.teste@email.com\",\n  \"login\": \"anatestelogin\",\n  \"password\": \"senhaAna123\",\n  \"userTypeId\": { \"id\": 1 },\n  \"address\": \"Rua Nova, 100\"\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ 'id': 1, 'name': 'João', 'email': 'joao@email.com', 'login': 'joao', 'userTypeId': { 'name': 'CLIENTE' }, 'address': 'Rua 1', 'lastUpdate': '2026-01-19T12:34:56.000Z' }"
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Erro de validação.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Erro de validação nos campos enviados.', 'instance': '/api/v1/user/create', 'errors': { 'email': 'E-mail is mandatory.', 'name': 'You must provide a name.' } }"
                )
            )
        )
    })
    @PostMapping("/create")
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest);

    @Operation(
        summary = "Realiza a atualização de um usuário.",
        description = "Atualiza os dados de um usuário existente pelo ID.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserUpdateRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"Carlos Teste\",\n  \"email\": \"carlos.teste@email.com\",\n  \"userTypeId\": { \"id\": 2 },\n  \"address\": \"Rua Teste, 200\"\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "User updated successfully")
            )
        ),
        @ApiResponse(responseCode = "400", description = "Erro de validação.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Erro de validação nos campos enviados.', 'instance': '/api/v1/user/update/{id}', 'errors': { 'email': 'E-mail is mandatory.' } }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'User Not Found', 'status': 404, 'detail': 'User with id 1 not found', 'instance': '/api/v1/user/update/1' }"
                )
            )
        )
    })
    @PatchMapping("/update/{id}")
    ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO userRequest);

    @Operation(
        summary = "Realiza a exclusão de um usuário.",
        description = "Exclui um usuário do sistema pelo ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuário excluído com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "User deleted successfully")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'User Not Found', 'status': 404, 'detail': 'User not found', 'instance': '/api/v1/user/delete' }"
                )
            )
        )
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteUser(@RequestParam Long id);

    @Operation(
        summary = "Troca a senha do usuário.",
        description = "Troca a senha do usuário informado pelo id.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ChangePasswordRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"oldPassword\": \"senha456\",\n  \"newPassword\": \"novaSenha456\"\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "Password changed successfully.")
            )
        ),
        @ApiResponse(responseCode = "400", description = "Erro de validação ou senha incorreta.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Nova senha não pode ser nula ou composta apenas por espaços.', 'instance': '/api/v1/user/change-password/{id}' }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'User Not Found', 'status': 404, 'detail': 'User with id 1 not found', 'instance': '/api/v1/user/change-password/1' }"
                )
            )
        )
    })
    @PostMapping("/change-password/{id}")
    ResponseEntity<String> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO);

    @Operation(
        summary = "Valida o login do usuário.",
        description = "Valida as credenciais de login do usuário. Retorna sucesso ou falha.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "login", description = "Login do usuário", required = true, example = "joaosilva"),
            @io.swagger.v3.oas.annotations.Parameter(name = "password", description = "Senha do usuário", required = true, example = "senha123")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string"),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "Login successful"
                )
            )
        ),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string"),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "Invalid credentials"
                )
            )
        )
    })
    @PostMapping("/validate-login")
    ResponseEntity<String> validateLogin(
        @RequestParam String login,
        @RequestParam String password
    );

    @Operation(
        summary = "Pesquisa todos os usuários cadastrados.",
        description = "Retorna uma lista paginada de todos os usuários cadastrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ { 'id': 1, 'name': 'João', 'email': 'joao@email.com', 'login': 'joao', 'userTypeId': { 'name': 'CLIENTE' }, 'address': 'Rua 1', 'lastUpdate': '2026-01-19T12:34:56.000Z' } ]"
                )
            )
        )
    })
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUsers(@RequestParam int page, @RequestParam int size);

    @Operation(
        summary = "Busca usuários pelo nome.",
        description = "Retorna uma lista de todos os usuários encontrados com o nome pesquisado."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ { 'id': 1, 'name': 'João', 'email': 'joao@email.com', 'login': 'joao', 'userTypeId': { 'name': 'CLIENTE' }, 'address': 'Rua 1', 'lastUpdate': '2026-01-19T12:34:56.000Z' } ]"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'User Not Found', 'status': 404, 'detail': 'User not found', 'instance': '/api/v1/user/find-by-name' }"
                )
            )
        )
    })
    @GetMapping("/find-by-name")
    ResponseEntity<List<UserResponseDTO>> findUsersByName(@RequestParam String name);
}
