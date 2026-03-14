package br.com.tastemanager.infrastructure.controller;

import br.com.tastemanager.application.service.MenuItemService;
import br.com.tastemanager.infrastructure.controller.openapi.MenuItemControllerDocs;
import br.com.tastemanager.shared.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuItemResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MenuItemController implements MenuItemControllerDocs {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @Override
    @PostMapping("/restaurants/{restaurantId}/menu-items")
    public ResponseEntity<MenuItemResponseDTO> createMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody MenuItemRequestDTO requestDTO) {

        MenuItemResponseDTO response = menuItemService.createMenuItem(restaurantId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @GetMapping("/menu-items")
    public ResponseEntity<List<MenuItemResponseDTO>> findAllMenuItems(
            @RequestParam int page,
            @RequestParam int size) {

        List<MenuItemResponseDTO> response = menuItemService.findAllMenuItems(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/restaurants/{restaurantId}/menu-items")
    public ResponseEntity<List<MenuItemResponseDTO>> findMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItemResponseDTO> response = menuItemService.findMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @GetMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemResponseDTO> findMenuItemById(@PathVariable Long id) {
        MenuItemResponseDTO response = menuItemService.findMenuItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @PatchMapping("/menu-items/{id}")
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemUpdateRequestDTO requestDTO) {

        MenuItemResponseDTO response = menuItemService.updateMenuItem(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @DeleteMapping("/menu-items/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable Long id) {
        String response = menuItemService.deleteMenuItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/menu-items/search")
    public ResponseEntity<List<MenuItemResponseDTO>> findMenuItemsByName(@RequestParam String name) {
        List<MenuItemResponseDTO> response = menuItemService.findMenuItemsByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/menu-items/availability")
    public ResponseEntity<List<MenuItemResponseDTO>> findMenuItemsByAvailability(
            @RequestParam Boolean availableOnlyAtRestaurant) {
        List<MenuItemResponseDTO> response = menuItemService.findMenuItemsByAvailability(availableOnlyAtRestaurant);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
