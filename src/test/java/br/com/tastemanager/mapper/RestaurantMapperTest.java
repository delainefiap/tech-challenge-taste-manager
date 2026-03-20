package br.com.tastemanager.mapper;

import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.shared.mapper.RestaurantMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RestaurantMapperTest {

    private final RestaurantMapper restaurantMapper = Mappers.getMapper(RestaurantMapper.class);

    @Test
    void toResponseDTO_ShouldMapOwnerName() {
        User owner = new User();
        owner.setName("Carlos");

        Restaurant entity = new Restaurant();
        entity.setName("Taste");
        entity.setAddress("Rua 1");
        entity.setTypeKitchen("Italiana");
        entity.setOpeningHours("10-22");
        entity.setOwner(owner);

        RestaurantResponseDTO dto = restaurantMapper.toResponseDTO(entity);

        assertEquals("Taste", dto.getName());
        assertEquals("Carlos", dto.getOwnerName());
    }

    @Test
    void toResponseDTO_WhenOwnerIsNull_ShouldNotSetOwnerName() {
        Restaurant entity = new Restaurant();
        entity.setName("Taste");
        entity.setAddress("Rua 1");
        entity.setTypeKitchen("Italiana");
        entity.setOpeningHours("10-22");
        entity.setOwner(null);

        RestaurantResponseDTO dto = restaurantMapper.toResponseDTO(entity);

        assertEquals("Taste", dto.getName());
        assertEquals(null, dto.getOwnerName());
    }
}
