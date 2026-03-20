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

    @Test
    void toResponseDTO_WhenRestaurantIsNull_ShouldNotSetRestaurant() {
        Menu menu = new Menu();
        menu.setId(1L);
        menu.setName("Pizza");
        menu.setDescription("Desc");
        menu.setPrice(10.0);
        menu.setRestaurant(null);

        MenuResponseDTO dto = menuMapper.toResponseDTO(menu);

        assertEquals("Pizza", dto.getName());
        assertEquals(null, dto.getRestaurant());
    }

    @Test
    void toMenuByRestaurantResponseDTO_WhenRestaurantIsNull_ShouldNotSetRestaurant() {
        Menu menu = new Menu();
        menu.setRestaurantItemNumber(1L);
        menu.setName("Pizza");
        menu.setDescription("Desc");
        menu.setPrice(10.0);
        menu.setRestaurant(null);

        var dto = menuMapper.toMenuByRestaurantResponseDTO(menu);

        assertEquals(1L, dto.getRestaurantItemNumber());
        assertEquals(null, dto.getRestaurant());
    }

    @Test
    void updateEntityFromDTO_ShouldIgnoreBlankAndInvalidValues() {
        Menu entity = new Menu();
        entity.setName("Old");
        entity.setDescription("OldDesc");
        entity.setPrice(10.0);
        entity.setAvailableOnlyAtRestaurant(false);

        MenuUpdateRequestDTO dto = new MenuUpdateRequestDTO();
        dto.setName("   ");
        dto.setDescription("");
        dto.setPrice(0.0);
        dto.setAvailableOnlyAtRestaurant(null);
        dto.setImagePath(null);

        menuMapper.updateEntityFromDTO(dto, entity);

        assertEquals("Old", entity.getName());
        assertEquals("OldDesc", entity.getDescription());
        assertEquals(10.0, entity.getPrice());
        assertEquals(false, entity.getAvailableOnlyAtRestaurant());
    }

    @Test
    void toEntity_WhenRequestIsNull_ShouldReturnNull() {
        var entity = menuMapper.toEntity(null, new br.com.tastemanager.domain.entity.Restaurant());

        assertEquals(null, entity);
    }

    @Test
    void toResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
        MenuResponseDTO dto = menuMapper.toResponseDTO(null);

        assertEquals(null, dto);
    }

    @Test
    void toMenuByRestaurantResponseDTO_WhenEntityIsNull_ShouldReturnNull() {
        var dto = menuMapper.toMenuByRestaurantResponseDTO(null);

        assertEquals(null, dto);
    }

    @Test
    void toResponseDTO_WhenOwnerIsNull_ShouldNotSetOwnerName() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("R1");
        restaurant.setOwner(null);

        Menu menu = new Menu();
        menu.setName("Pizza");
        menu.setDescription("Desc");
        menu.setPrice(10.0);
        menu.setRestaurant(restaurant);

        MenuResponseDTO dto = menuMapper.toResponseDTO(menu);

        assertNotNull(dto.getRestaurant());
        assertEquals(null, dto.getRestaurant().getOwnerName());
    }

    @Test
    void updateEntityFromDTO_ShouldApplyImageAndAvailability() {
        Menu entity = new Menu();
        entity.setImagePath(null);
        entity.setAvailableOnlyAtRestaurant(false);

        MenuUpdateRequestDTO dto = new MenuUpdateRequestDTO();
        dto.setImagePath("/img.png");
        dto.setAvailableOnlyAtRestaurant(true);

        menuMapper.updateEntityFromDTO(dto, entity);

        assertEquals("/img.png", entity.getImagePath());
        assertEquals(true, entity.getAvailableOnlyAtRestaurant());
    }
}
