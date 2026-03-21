package br.com.tastemanager.infrastructure.controller;

import br.com.tastemanager.application.service.RestaurantService;
import br.com.tastemanager.infrastructure.controller.openapi.RestaurantControllerDocs;
import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController implements RestaurantControllerDocs {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO requestDTO) {
        var response = restaurantService.createRestaurant(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<RestaurantResponseDTO>> findAllRestaurants() {
        var response = restaurantService.findAllRestaurants();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/find-by-id")
    public ResponseEntity<RestaurantResponseDTO> findRestaurantById(@RequestParam Long id) {
        var response = restaurantService.findRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Long id, @Valid @RequestBody RestaurantRequestDTO requestDTO) {
        var response = restaurantService.updateRestaurant(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteRestaurant(@RequestParam Long id) {
        var response = restaurantService.deleteRestaurant(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}