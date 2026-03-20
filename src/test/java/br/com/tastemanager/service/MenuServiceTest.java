package br.com.tastemanager.service;

import br.com.tastemanager.application.service.MenuService;
import br.com.tastemanager.domain.entity.Menu;
import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.domain.repository.MenuRepository;
import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import br.com.tastemanager.shared.mapper.MenuMapper;
import br.com.tastemanager.shared.validator.MenuValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;
    @Mock
    private MenuMapper menuMapper;
    @Mock
    private MenuValidator menuValidator;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenu_WhenRequestHasDuplicateNames_ShouldThrow() {
        MenuRequestDTO item1 = new MenuRequestDTO();
        item1.setName("Pizza");
        MenuRequestDTO item2 = new MenuRequestDTO();
        item2.setName("Pizza");

        Restaurant restaurant = new Restaurant();
        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> menuService.createMenu(1L, List.of(item1, item2)));

        assertTrue(ex.getMessage().contains("duplicados"));
    }

    @Test
    void createMenu_ShouldAssignSequentialRestaurantItemNumbers() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        Menu topMenu = new Menu();
        topMenu.setRestaurantItemNumber(5L);

        MenuRequestDTO r1 = new MenuRequestDTO();
        r1.setName("Item 1");
        r1.setDescription("D1");
        r1.setPrice(10.0);

        MenuRequestDTO r2 = new MenuRequestDTO();
        r2.setName("Item 2");
        r2.setDescription("D2");
        r2.setPrice(12.0);

        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);
        when(menuRepository.findTopByRestaurantIdOrderByRestaurantItemNumberDesc(1L)).thenReturn(Optional.of(topMenu));

        when(menuMapper.toEntity(any(MenuRequestDTO.class), ArgumentMatchers.eq(restaurant))).thenAnswer(invocation -> {
            MenuRequestDTO dto = invocation.getArgument(0);
            Menu m = new Menu();
            m.setName(dto.getName());
            m.setRestaurant(restaurant);
            return m;
        });

        when(menuRepository.saveAll(any(List.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(menuMapper.toMenuByRestaurantResponseDTO(any(Menu.class))).thenAnswer(invocation -> {
            Menu menu = invocation.getArgument(0);
            MenuByRestaurantResponseDTO dto = new MenuByRestaurantResponseDTO();
            dto.setRestaurantItemNumber(menu.getRestaurantItemNumber());
            dto.setName(menu.getName());
            return dto;
        });

        List<MenuByRestaurantResponseDTO> response = menuService.createMenu(1L, List.of(r1, r2));

        assertEquals(2, response.size());
        assertEquals(6L, response.get(0).getRestaurantItemNumber());
        assertEquals(7L, response.get(1).getRestaurantItemNumber());
    }

    @Test
    void findAllMenuItems_WithInvalidPage_ShouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> menuService.findAllMenuItems(0, 10));

        assertEquals("Parâmetro 'page' deve ser maior ou igual a 1", ex.getMessage());
    }

    @Test
    void findAllMenuItems_WithInvalidSize_ShouldThrow() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> menuService.findAllMenuItems(1, 0));

        assertEquals("Parâmetro 'size' deve ser maior ou igual a 1", ex.getMessage());
    }

    @Test
    void findAllMenuItems_ShouldReturnMappedPage() {
        Menu menu = new Menu();
        menu.setId(1L);
        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setId(1L);

        when(menuRepository.findAll(any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(menu)));
        when(menuMapper.toResponseDTO(menu)).thenReturn(dto);

        List<MenuResponseDTO> result = menuService.findAllMenuItems(1, 5);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void findMenuItemsByRestaurant_ShouldMapResult() {
        Menu menu = new Menu();
        menu.setRestaurantItemNumber(2L);
        MenuByRestaurantResponseDTO dto = new MenuByRestaurantResponseDTO();
        dto.setRestaurantItemNumber(2L);

        when(menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(1L)).thenReturn(List.of(menu));
        when(menuMapper.toMenuByRestaurantResponseDTO(menu)).thenReturn(dto);

        List<MenuByRestaurantResponseDTO> response = menuService.findMenuItemsByRestaurant(1L);

        assertEquals(1, response.size());
        verify(menuValidator).validateRestaurant(1L);
    }

    @Test
    void findMenuItemById_ShouldMapResult() {
        Menu menu = new Menu();
        menu.setId(11L);
        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setId(11L);

        when(menuRepository.findById(11L)).thenReturn(Optional.of(menu));
        when(menuMapper.toResponseDTO(menu)).thenReturn(dto);

        MenuResponseDTO response = menuService.findMenuItemById(11L);

        assertEquals(11L, response.getId());
        verify(menuValidator).validateMenuItemExists(11L);
    }

    @Test
    void updateMenuItem_WhenRestaurantItemNumberChanges_ShouldThrow() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        Menu menu = new Menu();
        menu.setRestaurant(restaurant);
        menu.setRestaurantItemNumber(3L);

        MenuUpdateRequestDTO dto = new MenuUpdateRequestDTO();
        dto.setRestaurantItemNumber(10L);

        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);
        when(menuValidator.validateMenuItemExists(1L, 2L)).thenReturn(menu);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> menuService.updateMenuItem(1L, 2L, dto));

        assertEquals("O restaurantItemNumber não pode ser alterado.", ex.getMessage());
    }

    @Test
    void updateMenuItem_WhenNameAndPriceChange_ShouldValidateAndSave() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        Menu menu = new Menu();
        menu.setId(2L);
        menu.setRestaurant(restaurant);
        menu.setRestaurantItemNumber(5L);
        menu.setName("Old");

        MenuUpdateRequestDTO request = new MenuUpdateRequestDTO();
        request.setName("New");
        request.setPrice(30.0);

        MenuResponseDTO responseDTO = new MenuResponseDTO();
        responseDTO.setId(2L);

        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);
        when(menuValidator.validateMenuItemExists(1L, 2L)).thenReturn(menu);
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuMapper.toResponseDTO(menu)).thenReturn(responseDTO);

        MenuResponseDTO response = menuService.updateMenuItem(1L, 2L, request);

        assertEquals(2L, response.getId());
        verify(menuValidator).validateMenuItemNameForUpdate("New", 1L, 2L);
        verify(menuValidator).validatePrice(30.0);
    }

    @Test
    void deleteMenuItemByRestaurantItemNumber_ShouldDeleteAndReturnMessage() {
        Restaurant restaurant = new Restaurant();
        Menu menu = new Menu();
        menu.setId(7L);

        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);
        when(menuRepository.findByRestaurantIdAndRestaurantItemNumber(1L, 2L)).thenReturn(Optional.of(menu));

        String response = menuService.deleteMenuItemByRestaurantItemNumber(1L, 2L);

        assertEquals("Item do cardápio excluído com sucesso", response);
        verify(menuRepository).delete(menu);
    }

    @Test
    void deleteAllMenuItemsByRestaurant_WhenNoItems_ShouldThrow() {
        Restaurant restaurant = new Restaurant();
        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);
        when(menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(1L)).thenReturn(List.of());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> menuService.deleteAllMenuItemsByRestaurant(1L));

        assertTrue(ex.getMessage().contains("Nenhum item do cardápio encontrado"));
    }

    @Test
    void deleteAllMenuItemsByRestaurant_WhenItemsExist_ShouldDeleteAll() {
        Restaurant restaurant = new Restaurant();
        Menu menu = new Menu();

        when(menuValidator.validateRestaurant(1L)).thenReturn(restaurant);
        when(menuRepository.findByRestaurantIdOrderByRestaurantItemNumberAsc(1L)).thenReturn(List.of(menu));

        String response = menuService.deleteAllMenuItemsByRestaurant(1L);

        assertTrue(response.contains("foram excluídos com sucesso"));
        verify(menuRepository).deleteAll(List.of(menu));
    }

    @Test
    void findMenuItemsByName_WhenNameIsInvalid_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> menuService.findMenuItemsByName("   "));
    }

    @Test
    void findMenuItemsByName_WhenValid_ShouldReturnMappedList() {
        Menu menu = new Menu();
        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setName("Pizza");

        when(menuRepository.findByNameContainingIgnoreCase("Pizza")).thenReturn(List.of(menu));
        when(menuMapper.toResponseDTO(menu)).thenReturn(dto);

        List<MenuResponseDTO> response = menuService.findMenuItemsByName("Pizza");

        assertEquals(1, response.size());
        assertEquals("Pizza", response.get(0).getName());
    }

    @Test
    void findMenuItemsByAvailability_WithNullFilter_ShouldReturnAll() {
        Menu menu = new Menu();
        menu.setId(1L);
        MenuResponseDTO dto = new MenuResponseDTO();
        dto.setId(1L);

        when(menuRepository.findAll()).thenReturn(List.of(menu));
        when(menuMapper.toResponseDTO(menu)).thenReturn(dto);

        List<MenuResponseDTO> response = menuService.findMenuItemsByAvailability(null);

        assertEquals(1, response.size());
        verify(menuRepository, times(1)).findAll();
    }

    @Test
    void findMenuItemsByAvailability_WithBooleanFilter_ShouldUseFilteredQuery() {
        Menu menu = new Menu();
        MenuResponseDTO dto = new MenuResponseDTO();

        when(menuRepository.findByAvailableOnlyAtRestaurant(true)).thenReturn(List.of(menu));
        when(menuMapper.toResponseDTO(menu)).thenReturn(dto);

        List<MenuResponseDTO> response = menuService.findMenuItemsByAvailability(true);

        assertEquals(1, response.size());
        verify(menuRepository).findByAvailableOnlyAtRestaurant(true);
    }
}
