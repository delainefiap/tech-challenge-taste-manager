package br.com.tastemanager.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuUpdateRequestDTO {

    private String name;
    private String description;
    private Double price;
    private Long restaurantItemNumber;

    @JsonProperty("availableOnlyAtRestaurant")
    private Boolean availableOnlyAtRestaurant;
    private String imagePath;

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

    public Long getRestaurantItemNumber() {
        return restaurantItemNumber;
    }

    public void setRestaurantItemNumber(Long restaurantItemNumber) {
        this.restaurantItemNumber = restaurantItemNumber;
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
