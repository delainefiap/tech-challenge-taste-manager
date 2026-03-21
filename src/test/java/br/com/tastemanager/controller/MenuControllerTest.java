package br.com.tastemanager.controller;

import br.com.tastemanager.application.service.MenuService;
import br.com.tastemanager.infrastructure.controller.MenuController;
import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenuItem_ShouldReturnCreated() {
        MenuRequestDTO request = new MenuRequestDTO();
        List<MenuRequestDTO> input = List.of(request);
        List<MenuByRestaurantResponseDTO> output = List.of(new MenuByRestaurantResponseDTO());

        when(menuService.createMenu(1L, input)).thenReturn(output);

        ResponseEntity<List<MenuByRestaurantResponseDTO>> response = menuController.createMenuItem(1L, input);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(output, response.getBody());
        verify(menuService).createMenu(1L, input);
    }

    @Test
    void findAllMenuItems_ShouldReturnOk() {
        List<MenuResponseDTO> output = List.of(new MenuResponseDTO());
        when(menuService.findAllMenuItems(1, 10)).thenReturn(output);

        ResponseEntity<List<MenuResponseDTO>> response = menuController.findAllMenuItems(1, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(output, response.getBody());
    }

    @Test
    void findMenuItemsByRestaurant_ShouldReturnOk() {
        List<MenuByRestaurantResponseDTO> output = List.of(new MenuByRestaurantResponseDTO());
        when(menuService.findMenuItemsByRestaurant(2L)).thenReturn(output);

        ResponseEntity<List<MenuByRestaurantResponseDTO>> response = menuController.findMenuItemsByRestaurant(2L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(output, response.getBody());
        verify(menuService).findMenuItemsByRestaurant(2L);
    }

    @Test
    void findMenuItemById_ShouldReturnOk() {
        MenuResponseDTO dto = new MenuResponseDTO();
        when(menuService.findMenuItemById(9L)).thenReturn(dto);

        ResponseEntity<MenuResponseDTO> response = menuController.findMenuItemById(9L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
        verify(menuService).findMenuItemById(9L);
    }

    @Test
    void updateMenuItem_ShouldReturnOk() {
        var request = new br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO();
        MenuResponseDTO dto = new MenuResponseDTO();
        when(menuService.updateMenuItem(1L, 2L, request)).thenReturn(dto);

        ResponseEntity<MenuResponseDTO> response = menuController.updateMenuItem(1L, 2L, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dto, response.getBody());
        verify(menuService).updateMenuItem(1L, 2L, request);
    }

    @Test
    void deleteMenuItem_ShouldReturnOk() {
        when(menuService.deleteMenuItemByRestaurantItemNumber(1L, 3L)).thenReturn("ok");

        ResponseEntity<String> response = menuController.deleteMenuItem(1L, 3L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("ok", response.getBody());
        verify(menuService).deleteMenuItemByRestaurantItemNumber(1L, 3L);
    }

    @Test
    void deleteAllMenuItems_ShouldReturnOk() {
        when(menuService.deleteAllMenuItemsByRestaurant(4L)).thenReturn("ok");

        ResponseEntity<String> response = menuController.deleteAllMenuItems(4L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("ok", response.getBody());
        verify(menuService).deleteAllMenuItemsByRestaurant(4L);
    }
}
