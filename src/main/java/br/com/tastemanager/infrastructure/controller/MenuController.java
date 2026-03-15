package br.com.tastemanager.infrastructure.controller;

import br.com.tastemanager.application.service.MenuService;
import br.com.tastemanager.infrastructure.controller.openapi.MenuItemControllerDocs;
import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.MenuUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.MenuByRestaurantResponseDTO;
import br.com.tastemanager.shared.dto.response.MenuResponseDTO;
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
public class MenuController implements MenuItemControllerDocs {

    private final MenuService menuItemService;

    public MenuController(MenuService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @PostMapping("/menu/create/{restaurantId}")
    public ResponseEntity<List<MenuByRestaurantResponseDTO>> createMenuItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody List<MenuRequestDTO> requestDTO) {

        List<MenuByRestaurantResponseDTO> response = menuItemService.createMenu(restaurantId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/menu/find-all")
    public ResponseEntity<List<MenuResponseDTO>> findAllMenuItems(
            @RequestParam int page,
            @RequestParam int size) {

        List<MenuResponseDTO> response = menuItemService.findAllMenuItems(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/menu/find-by-restaurant")
    public ResponseEntity<List<MenuByRestaurantResponseDTO>> findMenuItemsByRestaurant(@RequestParam("id") Long restaurantId) {
        List<MenuByRestaurantResponseDTO> response = menuItemService.findMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/menu/find-by-id/{id}")
    public ResponseEntity<MenuResponseDTO> findMenuItemById(@PathVariable Long id) {
        MenuResponseDTO response = menuItemService.findMenuItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/menu/update/{restaurantId}/{itemId}")
    public ResponseEntity<MenuResponseDTO> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @Valid @RequestBody MenuUpdateRequestDTO requestDTO) {

        MenuResponseDTO response = menuItemService.updateMenuItem(restaurantId, itemId, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/menu/delete/{restaurantId}/{itemId}")
    public ResponseEntity<String> deleteMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId) {
        String response = menuItemService.deleteMenuItem(restaurantId, itemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
