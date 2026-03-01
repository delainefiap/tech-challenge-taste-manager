package br.com.tastemanager.controller;

import br.com.tastemanager.controller.openapi.RestaurantControllerDocs;
import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
@Tag(name = "3. Restaurant Controller", description = "Operações de restaurante")
public class RestaurantController implements RestaurantControllerDocs {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO) {
        var response = restaurantService.createRestaurant(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurants() {
        var response = restaurantService.findAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> findRestaurantById(@PathVariable Long id) {
        var response = restaurantService.findRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDTO requestDTO) {
        var response = restaurantService.updateRestaurant(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<String> deleteRestaurant(@RequestParam Long id) {
        var response = restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}