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
                    value = "{\n  \"name\": \"Restaurante da Praça\",\n  \"cnpj\": \"12.345.678/0001-99\",\n  \"userId\": { \"id\": 1 }\n}"
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
                    example = "{ 'type': 'https://example.com/validation-error', 'title': 'Validation Error', 'status': 400, 'detail': 'Erro de validação nos campos enviados.', 'instance': '/api/v1/restaurant/create' }"
                )
            )
        )
    })
    @PostMapping("/create")
    ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO requestDTO);

    @Operation(
        summary = "Lista todos os restaurantes.",
        description = "Retorna todos os restaurantes cadastrados."
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
        description = "Retorna os dados de um restaurante pelo ID."
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
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Restaurant Not Found', 'status': 404, 'detail': 'Restaurant not found', 'instance': '/api/v1/restaurant/find-by-id' }"
                )
            )
        )
    })
    @GetMapping("/find-by-id")
    ResponseEntity<RestaurantResponseDTO> findRestaurantById(@RequestParam Long id);

    @Operation(
        summary = "Atualiza um restaurante.",
        description = "Atualiza os dados de um restaurante existente.",
        requestBody = @RequestBody(
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RestaurantRequestDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    value = "{\n  \"name\": \"Novo Nome do Restaurante\"\n}"
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
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Restaurant Not Found', 'status': 404, 'detail': 'Restaurant not found', 'instance': '/api/v1/restaurant/update/1' }"
                )
            )
        )
    })
    @PatchMapping("/update/{id}")
    ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO requestDTO);

    @Operation(
        summary = "Exclui um restaurante.",
        description = "Exclui um restaurante pelo ID."
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
                    example = "{ 'type': 'https://example.com/not-found', 'title': 'Restaurant Not Found', 'status': 404, 'detail': 'Restaurant not found', 'instance': '/api/v1/restaurant/delete' }"
                )
            )
        )
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteRestaurant(@RequestParam Long id);
}
