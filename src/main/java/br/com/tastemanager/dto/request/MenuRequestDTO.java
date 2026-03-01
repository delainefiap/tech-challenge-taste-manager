package br.com.tastemanager.dto.request;

import java.util.List;

public class MenuRequestDTO {

    private List<MenuItemRequestDTO> items;


    public List<MenuItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<MenuItemRequestDTO> items) {
        this.items = items;
    }
}