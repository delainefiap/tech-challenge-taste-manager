package br.com.tastemanager.shared.validator;

import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.repository.MenuItemRepository;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import br.com.tastemanager.domain.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RestaurantValidator {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuRepository;

    public RestaurantValidator(RestaurantRepository restaurantRepository, UserRepository userRepository, MenuItemRepository menuRepository) {
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