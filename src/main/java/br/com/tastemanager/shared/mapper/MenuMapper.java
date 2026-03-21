package br.com.tastemanager.shared.mapper;

import br.com.tastemanager.domain.entity.Menu;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MenuMapper {

    public Menu toEntity(MenuRequestDTO dto, Restaurant restaurant) {
        if (dto == null) {
            return null;
        }

        Menu menu = new Menu();
        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImagePath(dto.getImagePath());
        menu.setAvailableOnlyAtRestaurant(dto.getAvailableOnlyAtRestaurant());
        menu.setRestaurant(restaurant);

        return menu;
    }

    public MenuResponseDTO toResponseDTO(Menu entity) {
        if (entity == null) {
            return null;
        }

        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setId(entity.getId());
        dto.setRestaurantItemNumber(entity.getRestaurantItemNumber());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImagePath(entity.getImagePath());
        dto.setAvailableOnlyAtRestaurant(entity.getAvailableOnlyAtRestaurant());

        if (entity.getRestaurant() != null) {
            dto.setRestaurant(mapRestaurantBasic(entity.getRestaurant()));
        }

        return dto;
    }

    public MenuByRestaurantResponseDTO toMenuByRestaurantResponseDTO(Menu entity) {
        if (entity == null) {
            return null;
        }

        MenuByRestaurantResponseDTO dto = new MenuByRestaurantResponseDTO();
        dto.setRestaurantItemNumber(entity.getRestaurantItemNumber());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setImagePath(entity.getImagePath());
        dto.setAvailableOnlyAtRestaurant(entity.getAvailableOnlyAtRestaurant());

        if (entity.getRestaurant() != null) {
            dto.setRestaurant(mapRestaurantBasic(entity.getRestaurant()));
        }

        return dto;
    }

    public void updateEntityFromDTO(MenuUpdateRequestDTO dto, Menu entity) {
        if (dto == null || entity == null) {
            return;
        }

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            entity.setName(dto.getName());
        }

        if (dto.getDescription() != null && !dto.getDescription().trim().isEmpty()) {
            entity.setDescription(dto.getDescription());
        }

        if (dto.getPrice() != null && dto.getPrice() > 0) {
            entity.setPrice(dto.getPrice());
        }

        if (dto.getImagePath() != null) {
            entity.setImagePath(dto.getImagePath());
        }

        if (dto.getAvailableOnlyAtRestaurant() != null) {
            entity.setAvailableOnlyAtRestaurant(dto.getAvailableOnlyAtRestaurant());
        }
    }

    private RestaurantResponseDTO mapRestaurantBasic(Restaurant restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setId(restaurant.getId());
        dto.setName(restaurant.getName());
        dto.setAddress(restaurant.getAddress());
        dto.setTypeKitchen(restaurant.getTypeKitchen());
        dto.setOpeningHours(restaurant.getOpeningHours());
        if (restaurant.getOwner() != null) {
            dto.setOwnerName(restaurant.getOwner().getName());
        }
        return dto;
    }
}
