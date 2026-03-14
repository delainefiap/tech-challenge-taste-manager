package br.com.tastemanager.shared.validator;

import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.repository.MenuItemRepository;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuItemValidator {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemValidator(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant validateRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("ID do restaurante é obrigatório");
        }

        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado com ID: " + restaurantId));
    }

    public void validateMenuItemExists(Long menuItemId) {
        if (menuItemId == null) {
            throw new IllegalArgumentException("ID do item do cardápio é obrigatório");
        }

        if (!menuItemRepository.existsById(menuItemId)) {
            throw new IllegalArgumentException("Item do cardápio não encontrado com ID: " + menuItemId);
        }
    }

    public void validateMenuItemName(String name, Long restaurantId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do item do cardápio é obrigatório");
        }

        if (restaurantId != null && menuItemRepository.existsByNameAndRestaurantId(name.trim(), restaurantId)) {
            throw new IllegalArgumentException("Já existe um item com este nome no restaurante");
        }
    }

    public void validateMenuItemNameForUpdate(String name, Long restaurantId, Long currentItemId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do item do cardápio é obrigatório");
        }

        menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .filter(item -> !item.getId().equals(currentItemId))
                .filter(item -> item.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .ifPresent(item -> {
                    throw new IllegalArgumentException("Já existe outro item com este nome no restaurante");
                });
    }

    public void validatePrice(Double price) {
        if (price == null) {
            throw new IllegalArgumentException("Preço é obrigatório");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
    }

    public void validateMenuItemOwnership(Long menuItemId, Long restaurantId) {
        if (menuItemId == null || restaurantId == null) {
            throw new IllegalArgumentException("ID do item e ID do restaurante são obrigatórios");
        }

        if (!menuItemRepository.findByIdAndRestaurantId(menuItemId, restaurantId).isPresent()) {
            throw new IllegalArgumentException("Item não pertence ao restaurante especificado");
        }
    }
}
