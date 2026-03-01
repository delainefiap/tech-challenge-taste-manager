package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.response.ItemResponseDTO;
import br.com.tastemanager.entity.ItemMenu;
import br.com.tastemanager.entity.Menu;
import br.com.tastemanager.entity.Restaurant;
import br.com.tastemanager.exception.ItemMenuNotFoundException;
import br.com.tastemanager.mapper.ItemMenuMapper;
import br.com.tastemanager.repository.ItemMenuRepository;
import br.com.tastemanager.repository.MenuRepository;
import br.com.tastemanager.repository.RestaurantRepository;
import br.com.tastemanager.validator.ItemMenuValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemMenuService {

    private final ItemMenuRepository itemMenuRepository;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final ItemMenuMapper itemMenuMapper;
    private final ItemMenuValidator itemMenuValidator;

    public ItemMenuService(ItemMenuRepository itemMenuRepository,
                          MenuRepository menuRepository,
                          RestaurantRepository restaurantRepository,
                          ItemMenuMapper itemMenuMapper,
                          ItemMenuValidator itemMenuValidator) {
        this.itemMenuRepository = itemMenuRepository;
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
        this.itemMenuMapper = itemMenuMapper;
        this.itemMenuValidator = itemMenuValidator;
    }

    @Transactional
    public ItemResponseDTO createItem(Long restaurantId, MenuItemRequestDTO requestDTO) {
        // Validar se o restaurante existe
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        // Validar dados do item
        itemMenuValidator.validateItemMenuRequest(requestDTO);

        // Buscar ou criar menu para o restaurante
        Menu menu = menuRepository.findByRestaurantId(restaurantId)
                .orElseGet(() -> createMenuForRestaurant(restaurant));

        // Criar item
        ItemMenu itemMenu = itemMenuMapper.toEntity(requestDTO);
        itemMenu.setMenu(menu);

        ItemMenu savedItem = itemMenuRepository.save(itemMenu);

        return itemMenuMapper.toResponseDTO(savedItem);
    }

    public List<Map<String, Object>> findAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return itemMenuRepository.findAll(pageable).getContent().stream()
                .map(this::mapItemToResponse)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDTO> findItemsByRestaurant(Long restaurantId) {
        // Verificar se o restaurante existe
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new IllegalArgumentException("Restaurant not found");
        }

        List<ItemMenu> items = itemMenuRepository.findByMenuRestaurantId(restaurantId);
        return items.stream()
                .map(itemMenuMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ItemResponseDTO findItemById(Long id) {
        ItemMenu itemMenu = itemMenuRepository.findById(id)
                .orElseThrow(() -> new ItemMenuNotFoundException("Item not found with id: " + id));

        return itemMenuMapper.toResponseDTO(itemMenu);
    }

    @Transactional
    public ItemResponseDTO updateItem(Long id, MenuItemUpdateRequestDTO requestDTO) {
        ItemMenu existingItem = itemMenuRepository.findById(id)
                .orElseThrow(() -> new ItemMenuNotFoundException("Item not found with id: " + id));

        // Validar dados da atualização
        itemMenuValidator.validateItemMenuUpdateRequest(requestDTO);

        // Atualizar campos
        itemMenuMapper.updateItemFromDTO(requestDTO, existingItem);

        ItemMenu updatedItem = itemMenuRepository.save(existingItem);

        return itemMenuMapper.toResponseDTO(updatedItem);
    }

    @Transactional
    public String deleteItem(Long id) {
        ItemMenu itemMenu = itemMenuRepository.findById(id)
                .orElseThrow(() -> new ItemMenuNotFoundException("Item not found with id: " + id));

        itemMenuRepository.delete(itemMenu);

        return "Item deleted successfully";
    }

    private Menu createMenuForRestaurant(Restaurant restaurant) {
        Menu menu = new Menu();
        menu.setMenuId(restaurant.getId());
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    private Map<String, Object> mapItemToResponse(ItemMenu item) {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("itemMenuId", item.getItemMenuId());
        itemMap.put("name", item.getName());
        itemMap.put("description", item.getDescription());
        itemMap.put("price", item.getPrice());
        itemMap.put("photoPath", item.getPhotoPath());
        itemMap.put("availableOnlyAtRestaurant", item.getAvailableOnlyAtRestaurant());
        itemMap.put("menuId", item.getMenu().getMenuId());
        itemMap.put("restaurantId", item.getMenu().getRestaurant().getId());
        itemMap.put("restaurantName", item.getMenu().getRestaurant().getName());
        return itemMap;
    }
}
