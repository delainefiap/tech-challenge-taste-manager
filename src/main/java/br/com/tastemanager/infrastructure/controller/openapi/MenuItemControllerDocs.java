package br.com.tastemanager.infrastructure.controller.openapi;


import br.com.tastemanager.shared.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuItemResponseDTO;
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

import java.util.List;

public interface MenuItemControllerDocs {

    @Operation(
            summary = "Cria um item do cardápio para um restaurante",
            description = "Cria um novo item do cardápio associado a um restaurante específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item do cardápio criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
    })
    @PostMapping("/restaurants/{restaurantId}/menu-items")
    ResponseEntity<MenuItemResponseDTO> createMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequestDTO requestDTO);

    @Operation(
            summary = "Lista todos os itens do cardápio",
            description = "Retorna uma lista paginada de todos os itens do cardápio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso")
    })
    @GetMapping("/menu-items")
    ResponseEntity<List<MenuItemResponseDTO>> findAllMenuItems(
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
    @GetMapping("/restaurants/{restaurantId}/menu-items")
    ResponseEntity<List<MenuItemResponseDTO>> findMenuItemsByRestaurant(@PathVariable Long restaurantId);

    @Operation(
            summary = "Busca um item do cardápio por ID",
            description = "Retorna os detalhes de um item específico do cardápio"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @GetMapping("/menu-items/{id}")
    ResponseEntity<MenuItemResponseDTO> findMenuItemById(@PathVariable Long id);

    @Operation(
            summary = "Atualiza um item do cardápio",
            description = "Atualiza as informações de um item do cardápio existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PatchMapping("/menu-items/{id}")
    ResponseEntity<MenuItemResponseDTO> updateMenuItem(
            @PathVariable Long id,
            @RequestBody MenuItemUpdateRequestDTO requestDTO);

    @Operation(
            summary = "Exclui um item do cardápio",
            description = "Remove um item do cardápio do sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/menu-items/{id}")
    ResponseEntity<String> deleteMenuItem(@PathVariable Long id);
}
