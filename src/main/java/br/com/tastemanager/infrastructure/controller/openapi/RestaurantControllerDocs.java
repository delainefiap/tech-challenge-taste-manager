package br.com.tastemanager.infrastructure.controller.openapi;

import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
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

public interface RestaurantControllerDocs {

    @Operation(summary = "Cria um novo restaurante.", description = "Cria um restaurante com os dados fornecidos.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Restaurante criado com sucesso."),
        @ApiResponse(responseCode = "400", description = "Erro de validação.")
    })
    @PostMapping("/create")
    ResponseEntity<RestaurantResponseDTO> createRestaurant(@RequestBody RestaurantRequestDTO requestDTO);

    @Operation(summary = "Lista todos os restaurantes.", description = "Retorna todos os restaurantes cadastrados.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de restaurantes retornada com sucesso.")
    })
    @GetMapping("/find-all")
    ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurants();

    @Operation(summary = "Busca restaurante por ID.", description = "Retorna os dados de um restaurante pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante encontrado."),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    @GetMapping("/find-by-id")
    ResponseEntity<RestaurantResponseDTO> findRestaurantById(@RequestParam Long id);

    @Operation(summary = "Atualiza um restaurante.", description = "Atualiza os dados de um restaurante existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante atualizado com sucesso."),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    @PatchMapping("/update/{id}")
    ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequestDTO requestDTO);

    @Operation(summary = "Exclui um restaurante.", description = "Exclui um restaurante pelo ID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Restaurante excluído com sucesso."),
        @ApiResponse(responseCode = "404", description = "Restaurante não encontrado.")
    })
    @DeleteMapping("/delete")
    ResponseEntity<String> deleteRestaurant(@RequestParam Long id);
}
