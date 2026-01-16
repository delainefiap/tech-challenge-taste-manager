package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserTypeControllerDocs {

    @Operation(
            summary = "Cria um novo tipo de usuário.",
            description = "Adiciona um novo tipo de usuário ao sistema e retorna os dados do tipo criado."
    )
    @ApiResponse(responseCode = "201", description = "Tipo de usuário criado com sucesso.")
    @PostMapping("/create")
    ResponseEntity<UserTypeResponseDTO> createUserType(@RequestBody UserTypeRequestDTO userTypeRequestDTO);

    @Operation(
            summary = "Atualiza um tipo de usuário.",
            description = "Atualiza as informações de um tipo de usuário existente pelo ID."
    )
    @PatchMapping("/update/{id}")
    ResponseEntity<UserTypeResponseDTO> updateUserType(@PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody UserTypeRequestDTO userTypeRequestDTO);


    @Operation(
            summary = "Exclui um tipo de usuário.",
            description = "Remove um tipo de usuário do sistema pelo ID."
    )
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteUserType(@RequestParam Long id);

    @Operation(
            summary = "Busca todos os tipos de usuário.",
            description = "Retorna uma lista paginada de todos os tipos de usuário cadastrados."
    )
    @GetMapping("/find-all")
    ResponseEntity<?> findAllUserTypes(@RequestParam int page, @RequestParam int size);

    @Operation(
            summary = "Busca tipo de usuário por ID.",
            description = "Retorna os dados de um tipo de usuário específico pelo ID."
    )
    @GetMapping("/find/{id}")
    ResponseEntity<UserTypeResponseDTO> findUserTypeById(@PathVariable Long id);
}
