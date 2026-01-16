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
        description = "Cria um novo usuário no sistema e retorna os dados do usuário criado."
    )
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso.")
    @PostMapping("/create")
    ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest);

    @Operation(
        summary = "Realiza a atualização de um usuário.",
        description = "Atualiza os dados de um usuário existente pelo ID."
    )
    @PatchMapping("/update/{id}")
    ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDTO userRequest);

    @Operation(
        summary = "Realiza a exclusão de um usuário.",
        description = "Exclui um usuário do sistema pelo ID."
    )
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteUser(@RequestParam Long id);

    @Operation(
        summary = "Troca a senha do usuário.",
        description = "Altera a senha de um usuário identificado pelo ID."
    )
    @PostMapping("/change-password/{id}")
    ResponseEntity<String> changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO);

    @Operation(
        summary = "Valida o login do usuário.",
        description = "Valida as credenciais de login do usuário. Retorna sucesso ou falha."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login bem-sucedido."),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas.")
    })
    @PostMapping("/validate-login")
    ResponseEntity<String> validateLogin(@RequestParam String login, @RequestParam String password);

    @Operation(
        summary = "Pesquisa todos os usuários cadastrados.",
        description = "Retorna uma lista paginada de todos os usuários cadastrados."
    )
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUsers(@RequestParam int page, @RequestParam int size);

    @Operation(
            summary = "Procura usuários pelo nome.",
            description ="Retorna uma lista de todos os usuários encontrados com o nome pesquisado."
            )
    @GetMapping("/find-by-name")
    ResponseEntity<List<UserResponseDTO>> findUsersByName(@RequestParam String name);
}
