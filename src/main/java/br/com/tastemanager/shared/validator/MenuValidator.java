package br.com.tastemanager.shared.validator;

import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import jakarta.validation.ConstraintDefinitionException;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class MenuValidator {

    public void validateMenuRequest(MenuRequestDTO menuRequest) {
        if (menuRequest.getItems() == null || menuRequest.getItems().isEmpty()) {
            throw new IllegalArgumentException("The menu must contain at least one item");
        }

        menuRequest.getItems().forEach(item -> {
            if (item.getName() == null || item.getName().isBlank()) {
                throw new IllegalArgumentException("The item name cannot be empty");
            }
            if (item.getPrice() == null || item.getPrice() <= 0) {
                throw new ConstraintDefinitionException("The item price must be greater than zero.");
            }
        });
    }

    public void validateMenuExists(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu not found.");
        }
    }
}