package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.entity.ItemMenu;
import br.com.tastemanager.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(target = "menuId", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "items", ignore = true)
    Menu toEntity(MenuRequestDTO menuRequestDTO);

    @Mapping(target = "itemMenuId", ignore = true)
    @Mapping(target = "menu", ignore = true)
    ItemMenu toEntity(MenuItemRequestDTO menuItemRequestDTO);

    MenuResponseDTO toResponseDTO(Menu menu);


    default void updateItemFromDTO(MenuItemUpdateRequestDTO menuItemUpdateRequestDTO, @MappingTarget ItemMenu itemMenu) {
        if (menuItemUpdateRequestDTO.getName() != null) {
            itemMenu.setName(menuItemUpdateRequestDTO.getName());
        }
        if (menuItemUpdateRequestDTO.getPrice() != null) {
            itemMenu.setPrice(menuItemUpdateRequestDTO.getPrice());
        }
        if (menuItemUpdateRequestDTO.getPhotoPath() != null) {
            itemMenu.setPhotoPath(menuItemUpdateRequestDTO.getPhotoPath());
        }
        if (menuItemUpdateRequestDTO.getAvailableOnlyAtRestaurant() != null) {
            itemMenu.setAvailableOnlyAtRestaurant(menuItemUpdateRequestDTO.getAvailableOnlyAtRestaurant());
        }
        if (menuItemUpdateRequestDTO.getDescription() != null) {
            itemMenu.setDescription(menuItemUpdateRequestDTO.getDescription());
        }

    }
}