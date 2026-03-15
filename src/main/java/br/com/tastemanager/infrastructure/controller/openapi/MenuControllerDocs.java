package br.com.tastemanager.infrastructure.controller.openapi;

import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MenuControllerDocs {

    @Operation(
            summary = "Cria itens do cardápio para um restaurante",
            description = "Cria uma lista de novos itens do cardápio associados a um restaurante específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item do cardápio criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @PostMapping("/create/{restaurantId}")
    ResponseEntity<List<MenuByRestaurantResponseDTO>> createMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody List<MenuRequestDTO> requestDTO);

    @Operation(
            summary = "Lista todos os itens do cardápio",
            description = "Retorna uma lista paginada de todos os itens do cardápio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso")
    })
    @GetMapping("/find-all")
    ResponseEntity<List<MenuResponseDTO>> findAllMenuItems(
            @RequestParam int page,
            @RequestParam int size);

    @Operation(
            summary = "Lista itens do cardápio de um restaurante",
            description = "Retorna todos os itens do cardápio de um restaurante específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens do restaurante retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @GetMapping("/find-by-restaurant")
    ResponseEntity<List<MenuByRestaurantResponseDTO>> findMenuItemsByRestaurant(@RequestParam("id") Long restaurantId);

    @Operation(
            summary = "Busca item por ID",
            description = "Retorna um item do cardápio pelo seu ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/find-by-id/{id}")
    ResponseEntity<MenuResponseDTO> findMenuItemById(@PathVariable Long id);

    @Operation(
            summary = "Atualiza um item do cardápio",
            description = "Atualiza as informações de um item do cardápio existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PutMapping("/update")
    ResponseEntity<MenuResponseDTO> updateMenuItem(
            @RequestParam Long restaurantId,
            @RequestParam Long itemId,
            @Valid @RequestBody MenuUpdateRequestDTO requestDTO);

    @Operation(
            summary = "Exclui um item do cardápio",
            description = "Remove um item do cardápio do sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/delete-item")
    ResponseEntity<String> deleteMenuItem(@RequestParam Long restaurantId, @RequestParam Long restaurantItemNumber);

    @Operation(
            summary = "Exclui todos os itens do cardápio de um restaurante",
            description = "Remove todos os itens do cardápio associados a um restaurante"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Itens excluídos com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @DeleteMapping("/delete-all/{restaurantId}")
    ResponseEntity<String> deleteAllMenuItems(@PathVariable Long restaurantId);

}