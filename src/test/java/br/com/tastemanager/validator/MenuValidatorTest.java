package br.com.tastemanager.validator;

import br.com.tastemanager.domain.entity.Menu;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.repository.MenuRepository;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import br.com.tastemanager.shared.exception.ItemMenuNotFoundException;
import br.com.tastemanager.shared.exception.RestaurantNotFoundException;
import br.com.tastemanager.shared.validator.MenuValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class MenuValidatorTest {

    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;
    private MenuValidator menuValidator;

    @BeforeEach
    void setUp() {
        menuRepository = Mockito.mock(MenuRepository.class);
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        menuValidator = new MenuValidator(menuRepository, restaurantRepository);
    }

    @Test
    void validateRestaurant_WhenFound_ShouldReturnRestaurant() {
        Restaurant restaurant = new Restaurant();
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        assertDoesNotThrow(() -> menuValidator.validateRestaurant(1L));
    }

    @Test
    void validateRestaurant_WhenIdIsNull_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> menuValidator.validateRestaurant(null));
    }

    @Test
    void validateRestaurant_WhenNotFound_ShouldThrow() {
        when(restaurantRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> menuValidator.validateRestaurant(9L));
    }

    @Test
    void validateMenuItemExistsById_WhenNotExists_ShouldThrow() {
        when(menuRepository.existsById(99L)).thenReturn(false);

        assertThrows(ItemMenuNotFoundException.class, () -> menuValidator.validateMenuItemExists(99L));
    }

    @Test
    void validateMenuItemExistsByRestaurantItemNumber_WhenFound_ShouldNotThrow() {
        when(menuRepository.findByRestaurantIdAndRestaurantItemNumber(1L, 2L)).thenReturn(Optional.of(new Menu()));

        assertDoesNotThrow(() -> menuValidator.validateMenuItemExists(1L, 2L));
    }

    @Test
    void validateMenuItemNameForUpdate_WhenDuplicateExists_ShouldThrow() {
        Menu existing = new Menu();
        existing.setId(2L);
        existing.setName("Pizza");

        when(menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(1L)).thenReturn(List.of(existing));

        assertThrows(IllegalArgumentException.class,
                () -> menuValidator.validateMenuItemNameForUpdate("Pizza", 1L, 1L));
    }

    @Test
    void validateMenuItemOwnership_WhenNotOwned_ShouldThrow() {
        when(menuRepository.findByIdAndRestaurantId(10L, 1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> menuValidator.validateMenuItemOwnership(10L, 1L));
    }

    @Test
    void validatePrice_WhenNegative_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> menuValidator.validatePrice(-1.0));
    }

    @Test
    void validateMenuItemName_WhenAlreadyExists_ShouldThrow() {
        when(menuRepository.existsByNameAndRestaurantId("Pizza", 1L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> menuValidator.validateMenuItemName("Pizza", 1L));
    }

    @Test
    void validateMenuItemExistsById_WhenIdIsNull_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> menuValidator.validateMenuItemExists(null));
    }

    @Test
    void validateRestaurantItemNumber_WhenDuplicate_ShouldThrow() {
        Menu existing = new Menu();
        existing.setId(2L);
        when(menuRepository.findByRestaurantIdAndRestaurantItemNumber(1L, 3L))
                .thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class,
                () -> menuValidator.validateRestaurantItemNumber(1L, 3, 1L));
    }

    @Test
    void validateRestaurantItemNumber_WhenSameItem_ShouldNotThrow() {
        Menu existing = new Menu();
        existing.setId(2L);
        when(menuRepository.findByRestaurantIdAndRestaurantItemNumber(1L, 3L))
                .thenReturn(Optional.of(existing));

        assertDoesNotThrow(() -> menuValidator.validateRestaurantItemNumber(1L, 3, 2L));
    }

    @Test
    void validateMenuItemExistsById_WhenExists_ShouldNotThrow() {
        when(menuRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> menuValidator.validateMenuItemExists(1L));
    }

    @Test
    void validateMenuItemName_WhenValid_ShouldNotThrow() {
        when(menuRepository.existsByNameAndRestaurantId("Pizza", 1L)).thenReturn(false);

        assertDoesNotThrow(() -> menuValidator.validateMenuItemName("Pizza", 1L));
    }

    @Test
    void validateMenuItemOwnership_WhenOwned_ShouldNotThrow() {
        when(menuRepository.findByIdAndRestaurantId(10L, 1L)).thenReturn(Optional.of(new Menu()));

        assertDoesNotThrow(() -> menuValidator.validateMenuItemOwnership(10L, 1L));
    }

    @Test
    void validateMenuItemName_WhenBlank_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> menuValidator.validateMenuItemName("  ", 1L));
    }

    @Test
    void validateMenuItemNameForUpdate_WhenBlank_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> menuValidator.validateMenuItemNameForUpdate(" ", 1L, 1L));
    }

    @Test
    void validateRestaurantItemNumber_WhenNull_ShouldNotThrow() {
        assertDoesNotThrow(() -> menuValidator.validateRestaurantItemNumber(1L, null, 1L));
    }
}
