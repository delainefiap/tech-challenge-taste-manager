package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserTypeControllerDocs {

    @Operation(
            summary = "Cria um novo tipo de usuário.",
            description = "Adiciona um novo tipo de usuário ao sistema e retorna os dados do tipo criado.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'name': 'cliente' }"
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'id': 1, 'name': 'cliente' }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Erro de validação.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/problem+json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Nome já existe ou inválido.', 'instance': '/api/v1/usertype/create' }"
                            )
                    )
            )
    })
    @PostMapping("/create")
    ResponseEntity<UserTypeResponseDTO> createUserType(@RequestBody UserTypeRequestDTO userTypeRequestDTO);

    @Operation(
            summary = "Atualiza um tipo de usuário.",
            description = "Atualiza as informações de um tipo de usuário existente pelo ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'name': 'dono_restaurante' }"
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de usuário atualizado com sucesso.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'id': 2, 'name': 'dono_restaurante' }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/problem+json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'type': 'https://example.com/not-found', 'title': 'UserType Not Found', 'status': 404, 'detail': 'Tipo de usuário não encontrado.', 'instance': '/api/v1/usertype/update/{id}' }"
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
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "Tipo de usuário deletado com sucesso."
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/problem+json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'type': 'https://example.com/not-found', 'title': 'UserType Not Found', 'status': 404, 'detail': 'Tipo de usuário não encontrado.', 'instance': '/api/v1/usertype/delete' }"
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
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "[{ 'id': 1, 'name': 'cliente' }, { 'id': 2, 'name': 'dono_restaurante' }]"
                            )
                    )
            )
    })
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUserTypes(@RequestParam int page, @RequestParam int size);

    @Operation(
            summary = "Busca tipo de usuário por ID.",
            description = "Retorna os dados de um tipo de usuário específico pelo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tipo de usuário encontrado.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'id': 1, 'name': 'cliente' }"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Tipo de usuário não encontrado.",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/problem+json",
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{ 'type': 'https://example.com/not-found', 'title': 'UserType Not Found', 'status': 404, 'detail': 'Tipo de usuário não encontrado.', 'instance': '/api/v1/usertype/find/{id}' }"
                            )
                    )
            )
    })
    @GetMapping("/find/{id}")
    ResponseEntity<UserTypeResponseDTO> findUserTypeById(@PathVariable Long id);
}
