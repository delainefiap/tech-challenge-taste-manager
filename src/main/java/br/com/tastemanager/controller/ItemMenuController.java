package br.com.tastemanager.controller;

import br.com.tastemanager.controller.openapi.ItemMenuControllerDocs;
import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import br.com.tastemanager.dto.response.ItemResponseDTO;
import br.com.tastemanager.service.ItemMenuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "4. Item Menu Controller", description = "Operações de itens do cardápio")
public class ItemMenuController implements ItemMenuControllerDocs {

    private final ItemMenuService itemMenuService;

    public ItemMenuController(ItemMenuService itemMenuService) {
        this.itemMenuService = itemMenuService;
    }

    @Override
    public ResponseEntity<ItemResponseDTO> createItem(
            @PathVariable Long restaurantId,
            @Valid @RequestBody MenuItemRequestDTO requestDTO) {

        ItemResponseDTO response = itemMenuService.createItem(restaurantId, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<?> findAllItems(@RequestParam int page, @RequestParam int size) {
        var response = itemMenuService.findAllItems(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<List<ItemResponseDTO>> findItemsByRestaurant(@PathVariable Long restaurantId) {
        List<ItemResponseDTO> response = itemMenuService.findItemsByRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ItemResponseDTO> findItemById(@PathVariable Long id) {
        ItemResponseDTO response = itemMenuService.findItemById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemUpdateRequestDTO requestDTO) {

        ItemResponseDTO response = itemMenuService.updateItem(id, requestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<String> deleteItem(@PathVariable Long id) {
        String response = itemMenuService.deleteItem(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
