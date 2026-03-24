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
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "restaurantId", description = "ID do restaurante", required = true, example = "1")
        },
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "[{\n  \"name\": \"cebola\",\n  \"description\": \"Pizza com molho de tomate, mussarela e manjericão.\",\n  \"price\": 525.50,\n  \"availableOnlyAtRestaurant\": true,\n  \"imagePath\": \"/images/pizza-margherita.jpg\"\n}, {\n  \"name\": \"bacon\",\n  \"description\": \"Lasanha de carne com molho branco.\",\n  \"price\": 530.00,\n  \"availableOnlyAtRestaurant\": true,\n  \"imagePath\": \"/images/lasanha.jpg\"\n}]"
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
                    example = "{\n  \"type\": \"https://example.com/validation-error\", \n  \"title\": \"Validation Error\", \n  \"status\": 400, \n  \"detail\": \"Erro de validação nos campos enviados.\", \n  \"instance\": \"/api/v1/menu/create/{restaurantId}\" \n}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Restaurant Not Found\", \n  \"status\": 404, \n  \"detail\": \"Restaurant with id 1 not found\", \n  \"instance\": \"/api/v1/menu/create/1\" \n}"
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
        description = "Retorna uma lista paginada de todos os itens do cardápio",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Página (a partir de 1)", required = true, example = "1"),
            @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamanho da página", required = true, example = "40")
        }
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
        description = "Retorna todos os itens do cardápio de um restaurante específico",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do restaurante", required = true, example = "1")
        }
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
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Restaurant Not Found\", \n  \"status\": 404, \n  \"detail\": \"Restaurant not found\", \n  \"instance\": \"/api/v1/menu/find-by-restaurant\" \n}"
                )
            )
        )
    })
    @GetMapping("/find-by-restaurant")
    ResponseEntity<List<MenuByRestaurantResponseDTO>> findMenuItemsByRestaurant(@RequestParam("id") Long restaurantId);

    @Operation(
        summary = "Busca item por ID",
        description = "Retorna um item do cardápio pelo seu ID",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do item", required = true, example = "2")
        }
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
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Menu Item Not Found\", \n  \"status\": 404, \n  \"detail\": \"Menu item not found\", \n  \"instance\": \"/api/v1/menu/find-by-id/1\" \n}"
                )
            )
        )
    })
    @GetMapping("/find-by-id/{id}")
    ResponseEntity<MenuResponseDTO> findMenuItemById(@PathVariable Long id);

    @Operation(
        summary = "Atualiza um item do cardápio",
        description = "Atualiza as informações de um item do cardápio existente",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "restaurantId", description = "ID do restaurante", required = true, example = "2"),
            @io.swagger.v3.oas.annotations.Parameter(name = "itemId", description = "ID do item a ser atualizado", required = true, example = "2")
        },
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = MenuUpdateRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"cebolas\",\n  \"description\": \"Pizza com molho de tomate, mussarela e manjericão.\",\n  \"price\": 525.50,\n  \"availableOnlyAtRestaurant\": true,\n  \"imagePath\": \"/images/pizza-margherita.jpg\"\n}"
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
                    example = "{\n  \"type\": \"https://example.com/validation-error\", \n  \"title\": \"Validation Error\", \n  \"status\": 400, \n  \"detail\": \"Erro de validação nos campos enviados.\", \n  \"instance\": \"/api/v1/menu/update\" \n}"
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Item não encontrado",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Menu Item Not Found\", \n  \"status\": 404, \n  \"detail\": \"Menu item not found\", \n  \"instance\": \"/api/v1/menu/update\" \n}"
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
        description = "Remove um item do cardápio do sistema",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "restaurantId", description = "ID do restaurante", required = true, example = "1"),
            @io.swagger.v3.oas.annotations.Parameter(name = "restaurantItemNumber", description = "Número do item no restaurante", required = true, example = "1")
        }
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
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Menu Item Not Found\", \n  \"status\": 404, \n  \"detail\": \"Menu item not found\", \n  \"instance\": \"/api/v1/menu/delete-item\" \n}"
                )
            )
        )
    })
    @DeleteMapping("/delete-item")
    ResponseEntity<String> deleteMenuItem(@RequestParam Long restaurantId, @RequestParam Long restaurantItemNumber);

    @Operation(
        summary = "Exclui todos os itens do cardápio de um restaurante",
        description = "Remove todos os itens do cardápio associados a um restaurante",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "restaurantId", description = "ID do restaurante", required = true, example = "1")
        }
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
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Restaurant Not Found\", \n  \"status\": 404, \n  \"detail\": \"Restaurant not found\", \n  \"instance\": \"/api/v1/menu/delete-all/1\" \n}"
                )
            )
        )
    })
    @DeleteMapping("/delete-all/{restaurantId}")
    ResponseEntity<String> deleteAllMenuItems(@PathVariable Long restaurantId);

}
