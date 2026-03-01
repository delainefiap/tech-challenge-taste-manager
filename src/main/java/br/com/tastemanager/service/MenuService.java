package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.request.MenuRequestDTO;
import br.com.tastemanager.dto.response.MenuResponseDTO;
import br.com.tastemanager.entity.ItemMenu;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.mapper.MenuMapper;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.validator.MenuValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuMapper menuMapper;
    private final MenuValidator menuValidator;

    public MenuService(MenuRepository menuRepository,
                       RestaurantRepository restaurantRepository,
                       MenuMapper menuMapper,
                       MenuValidator menuValidator) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuMapper = menuMapper;
        this.menuValidator = menuValidator;
    }

    public String createMenu(Long restaurantId, MenuRequestDTO menuRequest) {
        menuValidator.validateMenuRequest(menuRequest);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        Menu menu = menuRepository.findByRestaurantId(restaurantId)
                .orElseGet(() -> {
                    Menu newMenu = new Menu();
                    newMenu.setMenuId(restaurantId);
                    newMenu.setRestaurant(restaurant);
                    newMenu.setItems(new ArrayList<>());
                    return newMenu;
                });

        menuRequest.getItems().forEach(itemRequest -> {
            ItemMenu item = menuMapper.toEntity(itemRequest);
            item.setMenu(menu);
            menu.getItems().add(item);
        });

        Menu savedMenu = menuRepository.save(menu);

        return "Menu and items created successfully ";
    }

    public List<MenuResponseDTO> getMenusByRestaurant(Long restaurantId) {
        return menuRepository.findByRestaurantId(restaurantId).stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return menuRepository.findAll(pageable).getContent().stream()
                .flatMap(menu -> menu.getItems().stream())
                .map(item -> {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("itemMenuId", item.getItemMenuId());
                    itemMap.put("availableOnlyAtRestaurant", item.getAvailableOnlyAtRestaurant());
                    itemMap.put("description", item.getDescription());
                    itemMap.put("name", item.getName());
                    itemMap.put("photoPath", item.getPhotoPath());
                    itemMap.put("price", item.getPrice());
                    itemMap.put("menuId", item.getMenu().getMenuId());
                    return itemMap;
                })
                .collect(Collectors.toList());
    }

    public String updateMenu(Long id, MenuItemUpdateRequestDTO menuItemUpdateRequestDTO) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        ItemMenu itemToUpdate = menu.getItems().stream()
                .filter(item -> item.getItemMenuId().equals(menuItemUpdateRequestDTO.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        menuMapper.updateItemFromDTO(menuItemUpdateRequestDTO, itemToUpdate);

        Menu updatedMenu = menuRepository.save(menu);

        return "Item updated successfully ";
    }

    public void deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Menu não encontrado"));

        menu.getItems().clear();
        menuRepository.save(menu);

        menuRepository.deleteById(id);
    }

    public void deleteMenuItem(Long menuId, Long itemId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("Menu não encontrado"));

        ItemMenu itemToRemove = menu.getItems().stream()
                .filter(item -> item.getItemMenuId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));

        menu.getItems().remove(itemToRemove);
        menuRepository.save(menu);
    }
}