package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Cria um menu para um restaurante.", description = "Cria um novo menu associado a um restaurante.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Menu criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.")
    })
    @PostMapping("/create/{restaurantId}")
    ResponseEntity<String> createMenu(@PathVariable Long restaurantId, @RequestBody MenuRequestDTO menu);

    @Operation(summary = "Lista menus de um restaurante.", description = "Retorna todos os menus pertencentes a um restaurante especificado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Menus retornados com sucesso."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    @GetMapping("/restaurant/{restaurantId}")
    ResponseEntity<List<MenuResponseDTO>> getMenusByRestaurant(@PathVariable Long restaurantId);

    @Operation(summary = "Busca todos os menus paginados.", description = "Retorna uma lista paginada de menus.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de menus retornada com sucesso.")
    })
    @GetMapping("/find-all")
    ResponseEntity<?> getAllMenus(@RequestParam int page, @RequestParam int size);

    @Operation(summary = "Atualiza um menu.", description = "Atualiza os dados de um menu existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Menu atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Menu não encontrado.")
    })
    @PutMapping("/update/{id}")
    ResponseEntity<String> updateMenu(@PathVariable Long id, @RequestBody MenuItemUpdateRequestDTO menuItemRequest);

    @Operation(summary = "Exclui um menu.", description = "Remove um menu pelo ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Menu excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Menu não encontrado.")
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteMenu(@RequestParam Long menuId);

    @Operation(summary = "Exclui um item de menu.", description = "Remove um item de um menu específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item de menu excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Item ou menu não encontrado.")
    })
    @DeleteMapping("/delete-item")
    ResponseEntity<String> deleteMenuItem(@RequestParam Long menuId, @RequestParam Long itemId);
}
