package br.com.tastemanager.infrastructure.controller.openapi;

import br.com.tastemanager.shared.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.shared.dto.request.UserRequestDTO;
import br.com.tastemanager.shared.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                    value = "{\n  \"name\": \"Ana Teste\",\n  \"email\": \"ana.teste@email.com\",\n  \"login\": \"anatestelogin\",\n  \"password\": \"senhaAna123\",\n  \"userTypeId\": { \"id\": 2 },\n  \"address\": \"Rua Nova, 100\"\n}"
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
                    value = "{\n  \"id\": 1, \n  \"name\": \"João Silva\", \n  \"email\": \"joao.silva@email.com\", \n  \"login\": \"joaosilva\", \n  \"userTypeId\": { \"name\": \"CLIENTE\" }, \n  \"address\": \"Rua A, 123\", \n  \"lastUpdate\": \"2026-03-21T12:00:00.000Z\" \n}"
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Erro de validação.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/validation-error\", \n  \"title\": \"Validation Error\", \n  \"status\": 400, \n  \"detail\": \"Erro de validação nos campos enviados.\", \n  \"instance\": \"/api/v1/user/create\", \n  \"errors\": { \"email\": \"E-mail is mandatory.\", \"name\": \"You must provide a name.\" } \n}"
                )
            )
        )
    })
    @PostMapping("/create")
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest);

    @Operation(
        summary = "Realiza a atualização de um usuário.",
        description = "Atualiza os dados de um usuário existente pelo ID.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do usuário", required = true, example = "1")
        },
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
                    example = "{\n  \"type\": \"https://example.com/validation-error\", \n  \"title\": \"Validation Error\", \n  \"status\": 400, \n  \"detail\": \"Erro de validação nos campos enviados.\", \n  \"instance\": \"/api/v1/user/update/{id}\", \n  \"errors\": { \"email\": \"E-mail is mandatory.\" } \n}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"User Not Found\", \n  \"status\": 404, \n  \"detail\": \"User with id 1 not found\", \n  \"instance\": \"/api/v1/user/update/1\" \n}"
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
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"User Not Found\", \n  \"status\": 404, \n  \"detail\": \"User not found\", \n  \"instance\": \"/api/v1/user/delete\" \n}"
                )
            )
        ),
        @ApiResponse(responseCode = "409", description = "Violação de integridade referencial ao excluir o usuário.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://datatracker.ietf.org/doc/html/rfc7807#section-3.1\", \n  \"title\": \"Data Integrity Violation\", \n  \"status\": 409, \n  \"detail\": \"Não foi possível excluir o usuário pois ele está associado a outros registros (por exemplo, restaurantes).\", \n  \"instance\": \"/api/v1/user/delete\" \n}"
                )
            )
        )
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteUser(@RequestParam Long id);

    @Operation(
        summary = "Troca a senha do usuário.",
        description = "Troca a senha do usuário informado pelo id.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do usuário", required = true, example = "4")
        },
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
                    example = "{\n  \"type\": \"https://example.com/validation-error\", \n  \"title\": \"Validation Error\", \n  \"status\": 400, \n  \"detail\": \"Nova senha não pode ser nula ou composta apenas por espaços.\", \n  \"instance\": \"/api/v1/user/change-password/{id}\" \n}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"User Not Found\", \n  \"status\": 404, \n  \"detail\": \"User with id 1 not found\", \n  \"instance\": \"/api/v1/user/change-password/1\" \n}"
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
            @io.swagger.v3.oas.annotations.Parameter(name = "login", description = "Login do usuário", required = true, example = "anatestelogin"),
            @io.swagger.v3.oas.annotations.Parameter(name = "password", description = "Senha do usuário", required = true, example = "senhaAna123")
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
        description = "Retorna uma lista paginada de todos os usuários cadastrados.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Página (a partir de 1)", required = true, example = "1"),
            @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamanho da página", required = true, example = "40")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ {\n  \"id\": 1, \n  \"name\": \"João Silva\", \n  \"email\": \"joao.silva@email.com\", \n  \"login\": \"joaosilva\", \n  \"userTypeId\": { \"name\": \"CLIENTE\" }, \n  \"address\": \"Rua A, 123\", \n  \"lastUpdate\": \"2026-03-21T12:00:00.000Z\" \n}, {\n  \"id\": 2, \n  \"name\": \"Maria Oliveira\", \n  \"email\": \"maria.oliveira@email.com\", \n  \"login\": \"mariaoliveira\", \n  \"userTypeId\": { \"name\": \"DONO_RESTAURANTE\" }, \n  \"address\": \"Rua B, 456\", \n  \"lastUpdate\": \"2026-03-21T12:00:00.000Z\" \n} ]"
                )
            )
        )
    })
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUsers(@RequestParam int page, @RequestParam int size);

    @Operation(
        summary = "Busca usuários pelo nome.",
        description = "Retorna uma lista de todos os usuários encontrados com o nome pesquisado.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "name", description = "Nome a pesquisar", required = true, example = "João")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de usuários encontrada.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                array = @io.swagger.v3.oas.annotations.media.ArraySchema(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserResponseDTO.class)),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[ {\n  \"id\": 1, \n  \"name\": \"João Silva\", \n  \"email\": \"joao.silva@email.com\", \n  \"login\": \"joaosilva\", \n  \"userTypeId\": { \"name\": \"CLIENTE\" }, \n  \"address\": \"Rua A, 123\", \n  \"lastUpdate\": \"2026-03-21T12:00:00.000Z\" \n} ]"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"User Not Found\", \n  \"status\": 404, \n  \"detail\": \"User not found\", \n  \"instance\": \"/api/v1/user/find-by-name\" \n}"
                )
            )
        )
    })
    @GetMapping("/find-by-name")
    ResponseEntity<List<UserResponseDTO>> findUsersByName(@RequestParam String name);
}
