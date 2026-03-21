package br.com.tastemanager.infrastructure.controller.openapi;

import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
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

import java.util.List;

public interface UserTypeControllerDocs {

    @Operation(
        summary = "Cria um novo tipo de usuário.",
        description = "Endpoint para criar um novo tipo de usuário.",
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserTypeRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"CLIENTE\"\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserTypeResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ 'id': 1, 'name': 'cliente' }"
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Erro de validação.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Nome já existe ou inválido.', 'instance': '/api/v1/user-type/create' }"
                )
            )
        )
    })
    @PostMapping("/create")
    ResponseEntity<UserTypeResponseDTO> createUserType(@RequestBody UserTypeRequestDTO userTypeRequestDTO);

    @Operation(
        summary = "Atualiza um tipo de usuário existente.",
        description = "Endpoint para atualizar um tipo de usuário pelo ID.",
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserTypeRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"DONO_RESTAURANTE\"\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserTypeResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ 'id': 2, 'name': 'dono_restaurante' }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'UserType Not Found', 'status': 404, 'detail': 'Tipo de usuário não encontrado.', 'instance': '/api/v1/user-type/update/{id}' }"
                )
            )
        )
    })
    @PatchMapping("/update/{id}")
    ResponseEntity<UserTypeResponseDTO> updateUserType(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody UserTypeRequestDTO userTypeRequestDTO);

    @Operation(
        summary = "Exclui um tipo de usuário.",
        description = "Remove um tipo de usuário do sistema pelo ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuário excluído com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "Tipo de usuário deletado com sucesso.")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'UserType Not Found', 'status': 404, 'detail': 'Tipo de usuário não encontrado.', 'instance': '/api/v1/user-type/delete' }"
                )
            )
        )
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteUserType(@RequestParam Long id);

    @Operation(
        summary = "Busca todos os tipos de usuário.",
        description = "Retorna uma lista paginada de todos os tipos de usuário cadastrados."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de tipos de usuário encontrada.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserTypeResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[{ 'id': 1, 'name': 'cliente' }, { 'id': 2, 'name': 'dono_restaurante' }]"
                )
            )
        )
    })
    @GetMapping("/find-all")
    ResponseEntity<List<UserTypeResponseDTO>> findAllUserTypes(@RequestParam int page, @RequestParam int size);

    @Operation(
        summary = "Busca tipo de usuário por ID.",
        description = "Retorna os dados de um tipo de usuário específico pelo ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserTypeResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{ 'id': 1, 'name': 'cliente' }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'UserType Not Found', 'status': 404, 'detail': 'Tipo de usuário não encontrado.', 'instance': '/api/v1/user-type/find-by-id' }"
                )
            )
        )
    })
    @GetMapping("/find-by-id")
    ResponseEntity<UserTypeResponseDTO> findUserTypeById(@RequestParam Long id);
}
