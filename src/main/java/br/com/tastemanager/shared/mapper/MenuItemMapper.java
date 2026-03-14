package br.com.tastemanager.shared.mapper;

import br.com.tastemanager.domain.entity.MenuItem;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.shared.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuItemResponseDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    public MenuItem toEntity(MenuItemRequestDTO dto, Restaurant restaurant) {
        if (dto == null) {
            return null;
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setImagePath(dto.getImagePath());
        menuItem.setAvailableOnlyAtRestaurant(dto.getAvailableOnlyAtRestaurant());
        menuItem.setRestaurant(restaurant);

        return menuItem;
    }

    public MenuItemResponseDTO toResponseDTO(MenuItem entity) {
        if (entity == null) {
            return null;
        }

        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.setId(entity.getId());
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

    public void updateEntityFromDTO(MenuItemUpdateRequestDTO dto, MenuItem entity) {
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
        return dto;
    }
}
