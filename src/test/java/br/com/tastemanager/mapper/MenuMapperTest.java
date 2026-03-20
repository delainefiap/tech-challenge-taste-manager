package br.com.tastemanager.mapper;

import br.com.tastemanager.domain.entity.Menu;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import br.com.tastemanager.shared.mapper.MenuMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class MenuMapperTest {

    private final MenuMapper menuMapper = new MenuMapper();

    @Test
    void toResponseDTO_ShouldMapFieldsAndRestaurantOwnerName() {
        User owner = new User();
        owner.setName("Owner");

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("R1");
        restaurant.setAddress("A1");
        restaurant.setTypeKitchen("Brasileira");
        restaurant.setOpeningHours("08-18");
        restaurant.setOwner(owner);

        Menu menu = new Menu();
        menu.setId(10L);
        menu.setRestaurantItemNumber(3L);
        menu.setName("Pizza");
        menu.setDescription("Desc");
        menu.setPrice(45.0);
        menu.setRestaurant(restaurant);

        MenuResponseDTO dto = menuMapper.toResponseDTO(menu);

        assertEquals(10L, dto.getId());
        assertEquals(3L, dto.getRestaurantItemNumber());
        assertEquals("Pizza", dto.getName());
        assertNotNull(dto.getRestaurant());
        assertEquals("Owner", dto.getRestaurant().getOwnerName());
    }

    @Test
    void updateEntityFromDTO_ShouldApplyOnlyValidValues() {
        Menu entity = new Menu();
        entity.setName("Old");
        entity.setDescription("OldDesc");
        entity.setPrice(10.0);

        MenuUpdateRequestDTO dto = new MenuUpdateRequestDTO();
        dto.setName("New");
        dto.setDescription("NewDesc");
        dto.setPrice(22.5);

        menuMapper.updateEntityFromDTO(dto, entity);

        assertEquals("New", entity.getName());
        assertEquals("NewDesc", entity.getDescription());
        assertEquals(22.5, entity.getPrice());
    }
}

