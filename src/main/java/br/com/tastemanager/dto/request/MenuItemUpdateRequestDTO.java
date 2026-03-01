package br.com.tastemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuItemUpdateRequestDTO {

    private Long itemId;
    private String name;
    private String description;
    private Double price;

    @JsonProperty("availableOnlyAtRestaurant")
    private Boolean availableOnlyAtRestaurant;
    private String photoPath;

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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}