package br.com.tastemanager.validator;

import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.MenuRepository;
import br.com.tastemanager.domain.repository.RestaurantRepository;
import br.com.tastemanager.domain.repository.UserRepository;
import br.com.tastemanager.shared.validator.RestaurantValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class RestaurantValidatorTest {

    private RestaurantRepository restaurantRepository;
    private UserRepository userRepository;
    private MenuRepository menuRepository;
    private RestaurantValidator restaurantValidator;

    @BeforeEach
    void setUp() {
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        menuRepository = Mockito.mock(MenuRepository.class);
        restaurantValidator = new RestaurantValidator(restaurantRepository, userRepository, menuRepository);
    }

    @Test
    void validateOwner_WhenOwnerTypeIs2_ShouldNotThrow() {
        User owner = new User();
        UserType userType = new UserType();
        userType.setId(2L);
        owner.setUserTypeId(userType);

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        assertDoesNotThrow(() -> restaurantValidator.validateOwner(1L));
    }

    @Test
    void validateOwner_WhenOwnerTypeIsNot2_ShouldThrow() {
        User owner = new User();
        UserType userType = new UserType();
        userType.setId(1L);
        owner.setUserTypeId(userType);

        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));

        assertThrows(IllegalArgumentException.class, () -> restaurantValidator.validateOwner(1L));
    }

    @Test
    void validateRestaurantHasNoMenus_WhenHasMenus_ShouldThrow() {
        when(menuRepository.existsByRestaurantId(3L)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> restaurantValidator.validateRestaurantHasNoMenus(3L));
    }
}

