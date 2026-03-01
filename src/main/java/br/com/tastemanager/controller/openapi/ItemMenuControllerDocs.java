package br.com.tastemanager.controller.openapi;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.response.ItemResponseDTO;
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

public interface ItemMenuControllerDocs {

    @Operation(
            summary = "Cria um novo item no cardápio.",
            description = "Adiciona um item a um restaurante específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Item criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro de validação."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    @PostMapping("/restaurants/{restaurantId}/items")
    ResponseEntity<ItemResponseDTO> createItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequestDTO requestDTO
    );

    @Operation(
            summary = "Lista todos os itens do cardápio.",
            description = "Retorna uma lista paginada de todos os itens do cardápio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso.")
    })
    @GetMapping("/items/find-all")
    ResponseEntity<?> findAllItems(@RequestParam int page, @RequestParam int size);

    @Operation(
            summary = "Lista itens do cardápio de um restaurante.",
            description = "Retorna todos os itens do cardápio de um restaurante específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de itens do restaurante retornada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    @GetMapping("/restaurants/{restaurantId}/items")
    ResponseEntity<List<ItemResponseDTO>> findItemsByRestaurant(@PathVariable Long restaurantId);

    @Operation(
            summary = "Busca um item do cardápio por ID.",
            description = "Retorna os dados de um item específico pelo ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item encontrado."),
            @ApiResponse(responseCode = "404", description = "Item não encontrado.")
    })
    @GetMapping("/items/{id}")
    ResponseEntity<ItemResponseDTO> findItemById(@PathVariable Long id);

    @Operation(
            summary = "Atualiza um item do cardápio.",
            description = "Atualiza os dados de um item específico."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Item não encontrado."),
            @ApiResponse(responseCode = "400", description = "Erro de validação.")
    })
    @PatchMapping("/items/{id}")
    ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long id,
            @RequestBody MenuItemUpdateRequestDTO requestDTO
    );

    @Operation(
            summary = "Exclui um item do cardápio.",
            description = "Remove um item específico do cardápio."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Item não encontrado.")
    })
    @DeleteMapping("/items/{id}")
    ResponseEntity<String> deleteItem(@PathVariable Long id);
}
