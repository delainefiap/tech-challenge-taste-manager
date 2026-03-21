package br.com.tastemanager.shared.validator;

import br.com.tastemanager.domain.entity.Menu;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.repository.MenuRepository;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import br.com.tastemanager.shared.exception.ItemMenuNotFoundException;
import br.com.tastemanager.shared.exception.RestaurantNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MenuValidator {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuValidator(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant validateRestaurant(Long restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("ID do restaurante é obrigatório");
        }

        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurante não encontrado com ID: " + restaurantId));
    }

    public Menu validateMenuItemExists(Long restaurantId, Long restaurantItemNumber) {
        if (restaurantId == null || restaurantItemNumber == null) {
            throw new IllegalArgumentException("ID do restaurante e número do item são obrigatórios");
        }

        return menuRepository.findByRestaurantIdAndRestaurantItemNumber(restaurantId, restaurantItemNumber)
                .orElseThrow(() -> new ItemMenuNotFoundException("Item do cardápio não encontrado com ID: " + restaurantItemNumber + " para o restaurante ID: " + restaurantId));
    }

    public void validateMenuItemExists(Long menuItemId) {
        if (menuItemId == null) {
            throw new IllegalArgumentException("ID do item do cardápio é obrigatório");
        }

        if (!menuRepository.existsById(menuItemId)) {
            throw new ItemMenuNotFoundException("Item do cardápio não encontrado com ID: " + menuItemId);
        }
    }

    public void validateMenuItemName(String name, Long restaurantId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do item do cardápio é obrigatório");
        }

        if (restaurantId != null && menuRepository.existsByNameAndRestaurantId(name.trim(), restaurantId)) {
            throw new IllegalArgumentException("Já existe um item com este nome no restaurante");
        }
    }

    public void validateMenuItemNameForUpdate(String name, Long restaurantId, Long currentItemId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do item do cardápio é obrigatório");
        }

        menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(restaurantId)
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

        if (!menuRepository.findByIdAndRestaurantId(menuItemId, restaurantId).isPresent()) {
            throw new IllegalArgumentException("Item não pertence ao restaurante especificado");
        }
    }

    public void validateRestaurantItemNumber(Long restaurantId, Integer restaurantItemNumber, Long currentItemId) {
        if (restaurantItemNumber != null) {
            menuRepository.findByRestaurantIdAndRestaurantItemNumber(restaurantId, restaurantItemNumber.longValue())
                .ifPresent(item -> {
                    if (!item.getId().equals(currentItemId)) {
                        throw new IllegalArgumentException("O número do item do restaurante " + restaurantItemNumber + " já está em uso.");
                    }
                });
        }
    }
}
