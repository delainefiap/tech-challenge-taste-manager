package br.com.tastemanager.infrastructure.controller.openapi;

import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MenuControllerDocs {

    @Operation(
        summary = "Cria itens do cardápio para um restaurante",
        description = "Cria uma lista de novos itens do cardápio associados a um restaurante específico",
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[{\n  \"name\": \"Pizza Margherita\",\n  \"description\": \"Classic pizza with tomatoes and mozzarella\",\n  \"price\": 25.50\n}]"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Item do cardápio criado com sucesso",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuByRestaurantResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Erro de validação nos campos enviados.', 'instance': '/api/v1/menu/create/{restaurantId}' }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Restaurant Not Found', 'status': 404, 'detail': 'Restaurant with id 1 not found', 'instance': '/api/v1/menu/create/1' }"
                )
            )
        )
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
        @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuResponseDTO.class)
            )
        )
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
        @ApiResponse(responseCode = "200", description = "Lista de itens do restaurante retornada com sucesso",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuByRestaurantResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Restaurant Not Found', 'status': 404, 'detail': 'Restaurant not found', 'instance': '/api/v1/menu/find-by-restaurant' }"
                )
            )
        )
    })
    @GetMapping("/find-by-restaurant")
    ResponseEntity<List<MenuByRestaurantResponseDTO>> findMenuItemsByRestaurant(@RequestParam("id") Long restaurantId);

    @Operation(
        summary = "Busca item por ID",
        description = "Retorna um item do cardápio pelo seu ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Menu Item Not Found', 'status': 404, 'detail': 'Menu item not found', 'instance': '/api/v1/menu/find-by-id/1' }"
                )
            )
        )
    })
    @GetMapping("/find-by-id/{id}")
    ResponseEntity<MenuResponseDTO> findMenuItemById(@PathVariable Long id);

    @Operation(
        summary = "Atualiza um item do cardápio",
        description = "Atualiza as informações de um item do cardápio existente",
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuUpdateRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"Pizza Calabresa\",\n  \"description\": \"Delicious calabresa pizza\",\n  \"price\": 30.00\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Erro de validação nos campos enviados.', 'instance': '/api/v1/menu/update' }"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Menu Item Not Found', 'status': 404, 'detail': 'Menu item not found', 'instance': '/api/v1/menu/update' }"
                )
            )
        )
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
        @ApiResponse(responseCode = "200", description = "Item excluído com sucesso",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "Menu item deleted successfully")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Menu Item Not Found', 'status': 404, 'detail': 'Menu item not found', 'instance': '/api/v1/menu/delete-item' }"
                )
            )
        )
    })
    @DeleteMapping("/delete-item")
    ResponseEntity<String> deleteMenuItem(@RequestParam Long restaurantId, @RequestParam Long restaurantItemNumber);

    @Operation(
        summary = "Exclui todos os itens do cardápio de um restaurante",
        description = "Remove todos os itens do cardápio associados a um restaurante"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Itens excluídos com sucesso",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "All menu items for the restaurant deleted successfully")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Restaurant Not Found', 'status': 404, 'detail': 'Restaurant not found', 'instance': '/api/v1/menu/delete-all/1' }"
                )
            )
        )
    })
    @DeleteMapping("/delete-all/{restaurantId}")
    ResponseEntity<String> deleteAllMenuItems(@PathVariable Long restaurantId);

}
