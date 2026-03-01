package br.com.tastemanager.validator;

import br.com.tastemanager.dto.request.MenuItemRequestDTO;
import br.com.tastemanager.dto.request.MenuItemUpdateRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemMenuValidator {

    public void validateItemMenuRequest(MenuItemRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Item menu request cannot be null");
        }

        validateName(dto.getName());
        validateDescription(dto.getDescription());
        validatePrice(dto.getPrice());
    }

    public void validateItemMenuUpdateRequest(MenuItemUpdateRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Item menu update request cannot be null");
        }

        if (dto.getName() != null) {
            validateName(dto.getName());
        }

        if (dto.getDescription() != null) {
            validateDescription(dto.getDescription());
        }

        if (dto.getPrice() != null) {
            validatePrice(dto.getPrice());
        }
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be null or empty");
        }

        if (name.length() > 100) {
            throw new IllegalArgumentException("Item name cannot exceed 100 characters");
        }
    }

    private void validateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Item description cannot be null or empty");
        }

        if (description.length() > 500) {
            throw new IllegalArgumentException("Item description cannot exceed 500 characters");
        }
    }

    private void validatePrice(Double price) {
        if (price == null) {
            throw new IllegalArgumentException("Item price cannot be null");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Item price must be greater than zero");
        }

        if (price > 9999.99) {
            throw new IllegalArgumentException("Item price cannot exceed 9999.99");
        }
    }
}
