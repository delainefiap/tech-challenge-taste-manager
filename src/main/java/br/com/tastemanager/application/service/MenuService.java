package br.com.tastemanager.application.service;

import br.com.tastemanager.domain.entity.Menu;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.repository.MenuRepository;
import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import br.com.tastemanager.shared.mapper.MenuMapper;
import br.com.tastemanager.shared.validator.MenuValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final MenuValidator menuValidator;

    public MenuService(MenuRepository menuRepository,
                       MenuMapper menuMapper,
                       MenuValidator menuValidator) {
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
        this.menuValidator = menuValidator;
    }

    @Transactional
    public List<MenuByRestaurantResponseDTO> createMenu(Long restaurantId, List<MenuRequestDTO> requestDTOs) {
        logger.info("Criando novo item do cardápio para o restaurante ID: {}", restaurantId);

        Restaurant restaurant = menuValidator.validateRestaurant(restaurantId);

        Set<String> namesInRequest = new HashSet<>();
        for (MenuRequestDTO requestDTO : requestDTOs) {
            if (!namesInRequest.add(requestDTO.getName().trim())) {
                throw new IllegalArgumentException("A requisição contém nomes de itens de menu duplicados: " + requestDTO.getName());
            }
        }

        AtomicLong lastItemNumber = new AtomicLong(
            menuRepository.findTopByRestaurantIdOrderByRestaurantItemNumberDesc(restaurantId)
                .map(Menu::getRestaurantItemNumber)
                .orElse(0L)
        );

        List<Menu> menus = requestDTOs.stream()
                .map(requestDTO -> {
                    menuValidator.validateMenuItemName(requestDTO.getName(), restaurantId);
                    menuValidator.validatePrice(requestDTO.getPrice());
                    Menu menu = menuMapper.toEntity(requestDTO, restaurant);
                    menu.setRestaurantItemNumber(lastItemNumber.incrementAndGet());
                    return menu;
                })
                .collect(Collectors.toList());

        menus = menuRepository.saveAll(menus);

        logger.info("Item do cardápio criado com sucesso.");
        return menus.stream()
                .map(menuMapper::toMenuByRestaurantResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> findAllMenuItems(int page, int size) {
        logger.info("Buscando todos os itens do cardápio - Página: {}, Tamanho: {}", page, size);

        if (page < 1) {
            throw new IllegalArgumentException("Parâmetro 'page' deve ser maior ou igual a 1");
        }

        if (size < 1) {
            throw new IllegalArgumentException("Parâmetro 'size' deve ser maior ou igual a 1");
        }

        Pageable pageable = PageRequest.of(page - 1, size);
        return menuRepository.findAll(pageable)
                .getContent()
                .stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuByRestaurantResponseDTO> findMenuItemsByRestaurant(Long restaurantId) {
        logger.info("Buscando itens do cardápio do restaurante ID: {}", restaurantId);

        menuValidator.validateRestaurant(restaurantId);

        return menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(restaurantId)
                .stream()
                .map(menuMapper::toMenuByRestaurantResponseDTO)
                .collect(Collectors.toList());
    }

    public MenuResponseDTO findMenuItemById(Long id) {
        logger.info("Buscando item do cardápio por ID: {}", id);

        menuValidator.validateMenuItemExists(id);

        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item do cardápio com ID " + id + " não encontrado"));

        return menuMapper.toResponseDTO(menu);
    }

    @Transactional
    public MenuResponseDTO updateMenuItem(Long restaurantId, Long itemId, MenuUpdateRequestDTO requestDTO) {
        logger.info("Atualizando item do cardápio ID: {} para o restaurante ID: {}", itemId, restaurantId);

        menuValidator.validateRestaurant(restaurantId);
        Menu menu = menuValidator.validateMenuItemExists(restaurantId, itemId);

        if (requestDTO.getRestaurantItemNumber() != null && !requestDTO.getRestaurantItemNumber().equals(menu.getRestaurantItemNumber())) {
            throw new IllegalArgumentException("O restaurantItemNumber não pode ser alterado.");
        }

        if (requestDTO.getName() != null && !requestDTO.getName().equals(menu.getName())) {
            menuValidator.validateMenuItemNameForUpdate(requestDTO.getName(),
                    menu.getRestaurant().getId(), menu.getId());
        }

        if (requestDTO.getPrice() != null) {
            menuValidator.validatePrice(requestDTO.getPrice());
        }

        menuMapper.updateEntityFromDTO(requestDTO, menu);
        menu = menuRepository.save(menu);

        logger.info("Item do cardápio atualizado com sucesso. ID: {}", menu.getId());
        return menuMapper.toResponseDTO(menu);
    }

    @Transactional
    public String deleteMenuItem(Long restaurantId, Long itemId) {
        logger.info("Excluindo item do cardápio ID: {} do restaurante ID: {}", itemId, restaurantId);

        menuValidator.validateRestaurant(restaurantId);
        Menu menu = menuValidator.validateMenuItemExists(restaurantId, itemId);

        menuRepository.delete(menu);

        logger.info("Item do cardápio excluído com sucesso. ID: {}", menu.getId());
        return "Item do cardápio excluído com sucesso";
    }

    @Transactional
    public String deleteMenuItemByRestaurantItemNumber(Long restaurantId, Long restaurantItemNumber) {
        logger.info("Excluindo item do cardápio {} do restaurante ID: {}", restaurantItemNumber, restaurantId);

        menuValidator.validateRestaurant(restaurantId);
        Menu menu = menuRepository.findByRestaurantIdAndRestaurantItemNumber(restaurantId, restaurantItemNumber)
            .orElseThrow(() -> new IllegalArgumentException("Item do cardápio com número " + restaurantItemNumber + " não encontrado para o restaurante com ID " + restaurantId));

        menuRepository.delete(menu);

        logger.info("Item do cardápio excluído com sucesso. ID: {}", menu.getId());
        return "Item do cardápio excluído com sucesso";
    }

    @Transactional
    public String deleteAllMenuItemsByRestaurant(Long restaurantId) {
        logger.info("Excluindo todos os itens do cardápio do restaurante ID: {}", restaurantId);

        menuValidator.validateRestaurant(restaurantId);
        List<Menu> menus = menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(restaurantId);

        if (menus.isEmpty()) {
            throw new IllegalArgumentException("Nenhum item do cardápio encontrado para o restaurante com ID " + restaurantId);
        }

        menuRepository.deleteAll(menus);

        logger.info("Todos os itens do cardápio do restaurante ID: {} foram excluídos com sucesso", restaurantId);
        return "Todos os itens do cardápio do restaurante foram excluídos com sucesso";
    }

    public List<MenuResponseDTO> findMenuItemsByName(String name) {
        logger.info("Buscando itens do cardápio por nome: {}", name);

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome para busca é obrigatório");
        }

        return menuRepository.findByNameContainingIgnoreCase(name.trim())
                .stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<MenuResponseDTO> findMenuItemsByAvailability(Boolean availableOnlyAtRestaurant) {
        logger.info("Buscando itens do cardápio por disponibilidade: {}", availableOnlyAtRestaurant);

        if (availableOnlyAtRestaurant == null) {
            return menuRepository.findAll()
                    .stream()
                    .map(menuMapper::toResponseDTO)
                    .collect(Collectors.toList());
        }

        return menuRepository.findByAvailableOnlyAtRestaurant(availableOnlyAtRestaurant)
                .stream()
                .map(menuMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
