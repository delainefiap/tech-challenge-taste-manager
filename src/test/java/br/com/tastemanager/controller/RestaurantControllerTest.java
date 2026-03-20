package br.com.tastemanager.controller;

import br.com.tastemanager.application.service.RestaurantService;
import br.com.tastemanager.infrastructure.controller.RestaurantController;
import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_ShouldReturnCreated() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        RestaurantResponseDTO dto = new RestaurantResponseDTO();

        when(restaurantService.createRestaurant(request)).thenReturn(dto);

        ResponseEntity<RestaurantResponseDTO> response = restaurantController.createRestaurant(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
        verify(restaurantService).createRestaurant(request);
    }

    @Test
    void findAllRestaurants_ShouldReturnOk() {
        List<RestaurantResponseDTO> list = List.of(new RestaurantResponseDTO());
        when(restaurantService.findAllRestaurants()).thenReturn(list);

        ResponseEntity<List<RestaurantResponseDTO>> response = restaurantController.findAllRestaurants();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(list, response.getBody());
    }
}

