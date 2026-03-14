package br.com.tastemanager.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItemUpdateRequestDTO {

    private Long itemId;
    private String name;
    private String description;
    private Double price;

    @JsonProperty("availableOnlyAtRestaurant")
    private Boolean availableOnlyAtRestaurant;
    private String imagePath;

    public Long getId() {
        return itemId;
    }

    public void setId(Long menuId) {
        this.itemId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailableOnlyAtRestaurant() {
        return availableOnlyAtRestaurant;
    }

    public void setAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant) {
        this.availableOnlyAtRestaurant = availableOnlyAtRestaurant;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}