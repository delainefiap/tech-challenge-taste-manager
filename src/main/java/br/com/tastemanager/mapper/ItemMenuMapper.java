package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.response.ItemResponseDTO;
import br.com.tastemanager.entity.ItemMenu;
import org.springframework.stereotype.Component;

@Component
public class ItemMenuMapper {

    public ItemMenu toEntity(MenuItemRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        ItemMenu item = new ItemMenu();
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setPhotoPath(dto.getPhotoPath());
        item.setAvailableOnlyAtRestaurant(dto.isAvailableOnlyAtRestaurant());

        return item;
    }

    public ItemResponseDTO toResponseDTO(ItemMenu entity) {
        if (entity == null) {
            return null;
        }

        ItemResponseDTO dto = new ItemResponseDTO();
        dto.setItemMenuId(entity.getItemMenuId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setPhotoPath(entity.getPhotoPath());
        dto.setAvailableOnlyAtRestaurant(entity.getAvailableOnlyAtRestaurant());

        if (entity.getMenu() != null) {
            dto.setMenuId(entity.getMenu().getMenuId());
            if (entity.getMenu().getRestaurant() != null) {
                dto.setRestaurantId(entity.getMenu().getRestaurant().getId());
                dto.setRestaurantName(entity.getMenu().getRestaurant().getName());
            }
        }

        return dto;
    }

    public void updateItemFromDTO(MenuItemUpdateRequestDTO dto, ItemMenu entity) {
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

        if (dto.getPhotoPath() != null) {
            entity.setPhotoPath(dto.getPhotoPath());
        }

        if (dto.getAvailableOnlyAtRestaurant() != null) {
            entity.setAvailableOnlyAtRestaurant(dto.getAvailableOnlyAtRestaurant());
        }
    }
}
