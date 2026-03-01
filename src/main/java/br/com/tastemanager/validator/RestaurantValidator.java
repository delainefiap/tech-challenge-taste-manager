package br.com.tastemanager.validator;

import br.com.tastemanager.entity.User;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RestaurantValidator {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public RestaurantValidator(RestaurantRepository restaurantRepository, UserRepository userRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    public User validateOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!owner.getUserTypeId().getId().equals(2L)) {
            throw new IllegalArgumentException("Owner must be a user with UserType ID = 2");
        }

        return owner;
    }

    public void validateRestaurantName(String name) {
        if (restaurantRepository.existsByName(name)) {
            throw new IllegalArgumentException("Restaurant with this name already exists");
        }
    }

    public void validateRestaurantExists(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new IllegalArgumentException("Restaurant not found");
        }
    }

    public void validateRestaurantHasNoMenus(Long restaurantId) {
        if (menuRepository.existsByRestaurantId(restaurantId)) {
            throw new IllegalArgumentException("Cannot delete restaurant with associated menus");
        }
    }
}