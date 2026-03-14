package br.com.tastemanager.application.service;

import br.com.tastemanager.domain.entity.MenuItem;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.repository.MenuItemRepository;
import br.com.tastemanager.shared.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuItemResponseDTO;
import br.com.tastemanager.shared.mapper.MenuItemMapper;
import br.com.tastemanager.shared.validator.MenuItemValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    private static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;
    private final MenuItemValidator menuItemValidator;

    public MenuItemService(MenuItemRepository menuItemRepository,
                          MenuItemMapper menuItemMapper,
                          MenuItemValidator menuItemValidator) {
        this.menuItemRepository = menuItemRepository;
        this.menuItemMapper = menuItemMapper;
        this.menuItemValidator = menuItemValidator;
    }

    @Transactional
    public MenuItemResponseDTO createMenuItem(Long restaurantId, MenuItemRequestDTO requestDTO) {
        logger.info("Criando novo item do cardápio para o restaurante ID: {}", restaurantId);

        Restaurant restaurant = menuItemValidator.validateRestaurant(restaurantId);
        menuItemValidator.validateMenuItemName(requestDTO.getName(), restaurantId);
        menuItemValidator.validatePrice(requestDTO.getPrice());

        MenuItem menuItem = menuItemMapper.toEntity(requestDTO, restaurant);
        menuItem = menuItemRepository.save(menuItem);

        logger.info("Item do cardápio criado com sucesso. ID: {}", menuItem.getId());
        return menuItemMapper.toResponseDTO(menuItem);
    }

    public List<MenuItemResponseDTO> findAllMenuItems(int page, int size) {
        logger.info("Buscando todos os itens do cardápio - Página: {}, Tamanho: {}", page, size);

        Pageable pageable = PageRequest.of(page - 1, size);
        return menuItemRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(menuItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponseDTO> findMenuItemsByRestaurant(Long restaurantId) {
        logger.info("Buscando itens do cardápio do restaurante ID: {}", restaurantId);

        menuItemValidator.validateRestaurant(restaurantId);

        return menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(menuItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuItemResponseDTO findMenuItemById(Long id) {
        logger.info("Buscando item do cardápio por ID: {}", id);

        menuItemValidator.validateMenuItemExists(id);

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));

        return menuItemMapper.toResponseDTO(menuItem);
    }

    @Transactional
    public MenuItemResponseDTO updateMenuItem(Long id, MenuItemUpdateRequestDTO requestDTO) {
        logger.info("Atualizando item do cardápio ID: {}", id);

        menuItemValidator.validateMenuItemExists(id);

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));

        if (requestDTO.getName() != null && !requestDTO.getName().equals(menuItem.getName())) {
            menuItemValidator.validateMenuItemNameForUpdate(requestDTO.getName(),
                    menuItem.getRestaurant().getId(), id);
        }

        if (requestDTO.getPrice() != null) {
            menuItemValidator.validatePrice(requestDTO.getPrice());
        }

        menuItemMapper.updateEntityFromDTO(requestDTO, menuItem);
        menuItem = menuItemRepository.save(menuItem);

        logger.info("Item do cardápio atualizado com sucesso. ID: {}", id);
        return menuItemMapper.toResponseDTO(menuItem);
    }

    @Transactional
    public String deleteMenuItem(Long id) {
        logger.info("Excluindo item do cardápio ID: {}", id);

        menuItemValidator.validateMenuItemExists(id);

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio não encontrado"));

        menuItemRepository.delete(menuItem);

        logger.info("Item do cardápio excluído com sucesso. ID: {}", id);
        return "Item do cardápio excluído com sucesso";
    }

    public List<MenuItemResponseDTO> findMenuItemsByName(String name) {
        logger.info("Buscando itens do cardápio por nome: {}", name);

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome para busca é obrigatório");
        }

        return menuItemRepository.findByNameContainingIgnoreCase(name.trim())
                .stream()
                .map(menuItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemResponseDTO> findMenuItemsByAvailability(Boolean availableOnlyAtRestaurant) {
        logger.info("Buscando itens do cardápio por disponibilidade: {}", availableOnlyAtRestaurant);

        return menuItemRepository.findByAvailableOnlyAtRestaurant(availableOnlyAtRestaurant)
                .stream()
                .map(menuItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
