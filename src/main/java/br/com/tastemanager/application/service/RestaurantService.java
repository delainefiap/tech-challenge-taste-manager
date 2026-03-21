package br.com.tastemanager.application.service;

import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.shared.mapper.RestaurantMapper;
import br.com.tastemanager.shared.validator.RestaurantValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantValidator restaurantValidator;

    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantMapper restaurantMapper, RestaurantValidator restaurantValidator) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.restaurantValidator = restaurantValidator;
    }

    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO requestDTO) {
        User owner = restaurantValidator.validateOwner(requestDTO.getOwnerId());
        restaurantValidator.validateRestaurantName(requestDTO.getName());

        Restaurant restaurant = restaurantMapper.toEntity(requestDTO);
        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        return restaurantMapper.toResponseDTO(restaurant);
    }

    public List<RestaurantResponseDTO> findAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public RestaurantResponseDTO findRestaurantById(Long id) {
        restaurantValidator.validateRestaurantExists(id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        return restaurantMapper.toResponseDTO(restaurant);
    }

    public RestaurantResponseDTO updateRestaurant(Long id, RestaurantRequestDTO requestDTO) {
        restaurantValidator.validateRestaurantExists(id);
        User owner = restaurantValidator.validateOwner(requestDTO.getOwnerId());

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        restaurant.setName(requestDTO.getName());
        restaurant.setAddress(requestDTO.getAddress());
        restaurant.setTypeKitchen(requestDTO.getTypeKitchen());
        restaurant.setOpeningHours(requestDTO.getOpeningHours());
        restaurant.setOwner(owner);

        restaurantRepository.save(restaurant);

        return restaurantMapper.toResponseDTO(restaurant);
    }

    public String deleteRestaurant(Long id) {
        restaurantValidator.validateRestaurantExists(id);
        restaurantValidator.validateRestaurantHasNoMenus(id);
        restaurantRepository.deleteById(id);
        return "Restaurant deleted successfully";
    }
}