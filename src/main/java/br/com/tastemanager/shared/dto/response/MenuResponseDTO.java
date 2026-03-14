package br.com.tastemanager.shared.dto.response;

import java.util.List;

public class MenuResponseDTO {

    private Long menuId;
    private List<ItemResponseDTO> items;
    private RestaurantSummaryDTO restaurant;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public List<ItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemResponseDTO> items) {
        this.items = items;
    }

    public RestaurantSummaryDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantSummaryDTO restaurant) {
        this.restaurant = restaurant;
    }
}