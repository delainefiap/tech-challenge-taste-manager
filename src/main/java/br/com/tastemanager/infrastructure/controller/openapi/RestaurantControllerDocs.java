package br.com.tastemanager.infrastructure.controller.openapi;

import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
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

public interface RestaurantControllerDocs {

    @Operation(
        summary = "Cria um novo restaurante.",
        description = "Cria um restaurante com os dados fornecidos.",
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"Restaurante 2\",\n  \"address\": \"Rua Exemplo, 123\",\n  \"typeKitchen\": \"Italiana\",\n  \"openingHours\": \"10:00-22:00\",\n  \"ownerId\": 2\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "400", description = "Erro de validação.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/validation-error\", \n  \"title\": \"Validation Error\", \n  \"status\": 400, \n  \"detail\": \"Erro de validação nos campos enviados.\", \n  \"instance\": \"/api/v1/restaurant/create\" \n}"
                )
            )
        )
    })
    @PostMapping("/create")
    ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO requestDTO);

    @Operation(
        summary = "Lista todos os restaurantes.",
        description = "Retorna todos os restaurantes cadastrados.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "page", description = "Página (a partir de 1)", required = true, example = "1"),
            @io.swagger.v3.oas.annotations.Parameter(name = "size", description = "Tamanho da página", required = true, example = "40")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantResponseDTO.class)
            )
        )
    })
    @GetMapping("/find-all")
    ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurants();

    @Operation(
        summary = "Busca restaurante por ID.",
        description = "Retorna os dados de um restaurante pelo ID.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do restaurante", required = true, example = "1")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Restaurant Not Found\", \n  \"status\": 404, \n  \"detail\": \"Restaurant not found\", \n  \"instance\": \"/api/v1/restaurant/find-by-id\" \n}"
                )
            )
        )
    })
    @GetMapping("/find-by-id")
    ResponseEntity<RestaurantResponseDTO> findRestaurantById(@RequestParam Long id);

    @Operation(
        summary = "Atualiza um restaurante.",
        description = "Atualiza os dados de um restaurante existente.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do restaurante", required = true, example = "1")
        },
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"Restaurante Atualizado\",\n  \"address\": \"Rua Atualizada, 456\",\n  \"typeKitchen\": \"Japonesa\",\n  \"openingHours\": \"11:00-23:00\",\n  \"ownerId\": 2\n}"
                )
            )
        )
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantResponseDTO.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Restaurant Not Found\", \n  \"status\": 404, \n  \"detail\": \"Restaurant not found\", \n  \"instance\": \"/api/v1/restaurant/update/1\" \n}"
                )
            )
        )
    })
    @PatchMapping("/update/{id}")
    ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO requestDTO);

    @Operation(
        summary = "Exclui um restaurante.",
        description = "Exclui um restaurante pelo ID.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID do restaurante", required = true, example = "3")
        }
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante excluído com sucesso.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", example = "Restaurant deleted successfully")
            )
        ),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/problem+json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(
                    example = "{\n  \"type\": \"https://example.com/not-found\", \n  \"title\": \"Restaurant Not Found\", \n  \"status\": 404, \n  \"detail\": \"Restaurant not found\", \n  \"instance\": \"/api/v1/restaurant/delete\" \n}"
                )
            )
        )
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteRestaurant(@RequestParam Long id);
}
