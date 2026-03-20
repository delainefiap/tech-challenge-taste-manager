package br.com.tastemanager.service;

import br.com.tastemanager.application.service.RestaurantService;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.shared.mapper.RestaurantMapper;
import br.com.tastemanager.shared.validator.RestaurantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private RestaurantMapper restaurantMapper;
    @Mock
    private RestaurantValidator restaurantValidator;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_ShouldSetOwnerAndReturnMappedResponse() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Taste House");
        request.setAddress("Rua 1");
        request.setTypeKitchen("Brasileira");
        request.setOpeningHours("08:00-18:00");
        request.setOwnerId(2L);

        User owner = new User();
        owner.setId(2L);
        owner.setName("Owner");

        Restaurant entity = new Restaurant();
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setName("Taste House");

        when(restaurantValidator.validateOwner(2L)).thenReturn(owner);
        when(restaurantMapper.toEntity(request)).thenReturn(entity);
        when(restaurantMapper.toResponseDTO(entity)).thenReturn(dto);

        RestaurantResponseDTO response = restaurantService.createRestaurant(request);

        assertNotNull(response);
        assertEquals(owner, entity.getOwner());
        assertEquals("Taste House", response.getName());
        verify(restaurantValidator).validateRestaurantName("Taste House");
        verify(restaurantRepository).save(entity);
    }

    @Test
    void findAllRestaurants_ShouldReturnMappedList() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(1L);

        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(dto);

        List<RestaurantResponseDTO> response = restaurantService.findAllRestaurants();

        assertEquals(1, response.size());
        assertEquals(1L, response.get(0).getId());
    }

    @Test
    void findRestaurantById_ShouldValidateAndMap() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(7L);
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(7L);

        when(restaurantRepository.findById(7L)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toResponseDTO(restaurant)).thenReturn(dto);

        RestaurantResponseDTO response = restaurantService.findRestaurantById(7L);

        assertEquals(7L, response.getId());
        verify(restaurantValidator).validateRestaurantExists(7L);
    }

    @Test
    void updateRestaurant_ShouldPersistChangesAndReturnResponse() {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Novo Nome");
        request.setAddress("Rua 2");
        request.setTypeKitchen("Italiana");
        request.setOpeningHours("09:00-22:00");
        request.setOwnerId(3L);

        User owner = new User();
        owner.setId(3L);

        Restaurant entity = new Restaurant();
        entity.setId(10L);

        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(10L);
        dto.setName("Novo Nome");

        when(restaurantValidator.validateOwner(3L)).thenReturn(owner);
        when(restaurantRepository.findById(10L)).thenReturn(Optional.of(entity));
        when(restaurantMapper.toResponseDTO(entity)).thenReturn(dto);

        RestaurantResponseDTO response = restaurantService.updateRestaurant(10L, request);

        assertEquals("Novo Nome", entity.getName());
        assertEquals(owner, entity.getOwner());
        assertEquals(10L, response.getId());
        verify(restaurantValidator).validateRestaurantExists(10L);
        verify(restaurantRepository).save(entity);
    }

    @Test
    void deleteRestaurant_ShouldValidateAndDelete() {
        String response = restaurantService.deleteRestaurant(4L);

        assertEquals("Restaurant deleted successfully", response);
        verify(restaurantValidator).validateRestaurantExists(4L);
        verify(restaurantValidator).validateRestaurantHasNoMenus(4L);
        verify(restaurantRepository).deleteById(4L);
    }
}

